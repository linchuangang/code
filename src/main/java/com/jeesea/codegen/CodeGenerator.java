package com.jeesea.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.util.StopWatch;

import com.jeesea.codegen.model.AttributeModel;
import com.jeesea.codegen.model.BaseModel;
import com.jeesea.codegen.model.DaoModel;
import com.jeesea.codegen.model.EntityModel;
import com.jeesea.codegen.model.ModelWrapper;
import com.jeesea.codegen.model.ServiceImplModel;
import com.jeesea.codegen.model.ServiceModel;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 生成 Entity、MyBatis DAO 接口、MyBatis Mapper 文件、Service接口、Service实现等代码的工具类。
 *
 * @author Rocky
 */
public class CodeGenerator {

    private static final String CODE_AUTHOR = "Inrevo";

    private static final String TEMPLATE_PATH = "src/main/resources/template";
    private static final String TEMPLATE_ENTITY = "entity.ftl";
    private static final String TEMPLATE_MYBATIS_DAO = "mybatisDao.ftl";
    private static final String TEMPLATE_SERVICE = "service.ftl";
    private static final String TEMPLATE_SERVICE_IMPL = "serviceImpl.ftl";
    private static final String TEMPLATE_MYBATIS_DAO_MAPPER = "mybatisDaoMapper.ftl";

    private static final Map<String, String> COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS = new HashMap<>();

    static {
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("tinyint", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("tinyint unsigned", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("smallint", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("smallint unsigned", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("mediumint", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("mediumint unsigned", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("int", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("int unsigned", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("bigint", "Long");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("bigint unsigned", "Long");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("decimal", "Integer");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("float", "Float");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("float unsigned", "Float");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("double", "Double");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("double unsigned", "Double");

        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("char", "String");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("varchar", "String");

        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("time", "Date");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("date", "Date");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("datetime", "Date");
        COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.put("timestamp", "Date");
    }

    public static void main(String[] args) {
        // long startTime = System.currentTimeMillis();
        StopWatch stopWatch = new StopWatch("Code Generation Task");

        stopWatch.start("Init jdbc connection");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppSettings settings = context.getBean(AppSettings.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(context.getBean(DataSource.class));
        stopWatch.stop();

        try {
            stopWatch.start("Read metadata from database");
            List<ModelWrapper> models = jdbcTemplate.execute(new ModelInitCallback(settings));
            stopWatch.stop();

            stopWatch.start("Generate codes");
            generateCodes(models, settings);
            stopWatch.stop();
        } catch (Exception e) {
            e.printStackTrace();
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
        }

        context.close();
        System.out.printf("\n%s\n", stopWatch.prettyPrint());
    }

    private static class ModelInitCallback implements StatementCallback<List<ModelWrapper>> {

        private AppSettings settings;

        public ModelInitCallback(AppSettings settings) {
            this.settings = settings;
        }

        @Override
        public List<ModelWrapper> doInStatement(Statement stmt) throws SQLException, DataAccessException {
            DatabaseMetaData databaseMetaData = stmt.getConnection().getMetaData();

            List<ModelWrapper> results = new ArrayList<>();
            for (String tableName : settings.getEntityTableSet()) {
                ModelWrapper modelWrapper = genernateModel(stmt, databaseMetaData, tableName, settings);
                if (modelWrapper != null) {
                    results.add(modelWrapper);
                }
            }
            return results;
        }

    }

    private static ModelWrapper genernateModel(Statement stmt, DatabaseMetaData databaseMetaData, String tableName,
            AppSettings settings) throws SQLException {
        ResultSet tableRs = databaseMetaData.getTables(null, null, tableName, null);
        if (!tableRs.next()) {
            System.out.printf("WARNING: Table %s does not exists.\n", tableName);
            return null;
        }

        ResultSet pkRs = databaseMetaData.getPrimaryKeys(null, null, tableName);
        if (!pkRs.next()) {
            System.out.printf("WARNING: Table %s must have a PK column.\n", tableName);
            return null;
        }

        String pkColumnName = pkRs.getString("COLUMN_NAME").toLowerCase();

        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " LIMIT 1");
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (columnCount <= 0) {
            System.out.printf("WARNING: Table %s has no column.\n", tableName);
            return null;
        }

        EntityModel entity = new EntityModel();
        entity.setClassName(getEntityClassName(tableName, settings));
        entity.setPackageName(settings.getEntityPackageName());
        entity.setTableName(tableName);
        entity.setProjectPackageName(settings.getProjectPackageName());

        System.out.printf("Columns of table %s:\n\n", tableName);
        List<AttributeModel> attrs = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%d: %s %s(%d)\n", i, metaData.getColumnName(i), metaData.getColumnTypeName(i),
                    metaData.getColumnDisplaySize(i));
            if (i == columnCount) {
                System.out.println();
            }

            String columnName = metaData.getColumnName(i).toLowerCase();
            String columnType = metaData.getColumnTypeName(i).toLowerCase();
            int columnSize = metaData.getColumnDisplaySize(i);

            AttributeModel attr = new AttributeModel();
            attr.setPkColumn(columnName.equals(pkColumnName));
            attr.setPkAutoIncrement(metaData.isAutoIncrement(i));
            attr.setAttrName(getEntityAttrName(columnName, attr.isPkColumn()));
            attr.setAttrType(getEntityAttrType(metaData, i));
            attr.setColumnName(columnName);
            attr.setColumnType(columnType);
            attr.setColumnSize(columnSize);
            attr.setUpdatedColumn(columnName.equals("modify_time"));
            attr.setCreatedColumn(columnName.equals("creation_time") || columnName.equals("createtime"));
            attrs.add(attr);

            if (attr.isPkColumn()) {
                entity.setIdAttr(attr);
            }
            if (attr.isExtraDateAttr()) {
                entity.setContainExtraDateAttr(true);
            }
        }
        entity.setAttrs(attrs);

        DaoModel dao = new DaoModel();
        dao.setClassName(getDaoClassName(tableName, settings));
        dao.setPackageName(settings.getDaoPackageName());
        dao.setProjectPackageName(settings.getProjectPackageName());
        //Service接口
        ServiceModel service = new ServiceModel();
        service.setClassName(getEntityClassName(tableName, settings) + "Service");
        service.setPackageName(settings.getServicePackageName());
        service.setTableName(tableName);
        service.setEntityName(getEntityClassName(tableName, settings));
        service.setVariableName(toLowerCaseFirstOne(getEntityClassName(tableName, settings)));
        service.setProjectPackageName(settings.getProjectPackageName());
        //Service实现
        ServiceImplModel serviceImpl = new ServiceImplModel();
        serviceImpl.setClassName(getEntityClassName(tableName, settings) + "ServiceImpl");
        serviceImpl.setPackageName(settings.getServiceImplPackageName());
        serviceImpl.setTableName(tableName);
        serviceImpl.setEntityName(getEntityClassName(tableName, settings));
        serviceImpl.setVariableName(toLowerCaseFirstOne(getEntityClassName(tableName, settings)));
        serviceImpl.setProjectPackageName(settings.getProjectPackageName());

        ModelWrapper modelWrapper = new ModelWrapper();
        modelWrapper.setEntity(entity);
        modelWrapper.setDao(dao);
        modelWrapper.setService(service);
        modelWrapper.setServiceImpl(serviceImpl);
        return modelWrapper;
    }

    /**
     * 首字母小写
     * 
     * @param name
     * @return
     */
    public static String toLowerCaseFirstOne(String name) {
        char[] ch = name.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (i == 0) {
                ch[i] = Character.toLowerCase(ch[i]);
            }
        }
        StringBuffer a = new StringBuffer();
        a.append(ch);
        return a.toString();
    }

    private static void generateCodes(List<ModelWrapper> models, AppSettings settings) throws Exception {
        if (models == null || models.isEmpty()) {
            return;
        }

        Configuration cfg = new Configuration();
        cfg.setWhitespaceStripping(true);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));

        for (ModelWrapper modelWrapper : models) {
            EntityModel entityModel = modelWrapper.getEntity();
            DaoModel daoModel = modelWrapper.getDao();
            ServiceModel serviceModel = modelWrapper.getService();
            ServiceImplModel serviceImplModel = modelWrapper.getServiceImpl();

            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("entity", entityModel);
            modelMap.put("dao", daoModel);
            modelMap.put("service", serviceModel);
            modelMap.put("serviceImpl", serviceImplModel);
            modelMap.put("creationDate", DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));
            modelMap.put("currentYear", DateFormatUtils.format(new Date(), "yyyy"));
            modelMap.put("author", CODE_AUTHOR);

            Template template = cfg.getTemplate(TEMPLATE_ENTITY);
            doGenerate(entityModel, template, modelMap, "java", settings);

            template = cfg.getTemplate(TEMPLATE_MYBATIS_DAO);
            doGenerate(daoModel, template, modelMap, "java", settings);

            template = cfg.getTemplate(TEMPLATE_MYBATIS_DAO_MAPPER);
            doGenerate(daoModel, template, modelMap, "xml", settings);

            template = cfg.getTemplate(TEMPLATE_SERVICE);
            doGenerate(serviceModel, template, modelMap, "java", settings);

            template = cfg.getTemplate(TEMPLATE_SERVICE_IMPL);
            doGenerate(serviceImplModel, template, modelMap, "java", settings);
        }
    }

    private static void doGenerate(BaseModel model, Template template, Map<String, Object> modelMap, String fileSuffix,
            AppSettings settings) throws Exception {
        File modelFile = new File(settings.getCodegenPath(), model.getClassFilePath() + "." + fileSuffix);
        if (modelFile.exists()) {
            System.out.printf("WARNING: File %s is already exists!\n", modelFile.getPath());
            return;
        }

        modelFile.getParentFile().mkdirs();
        try (Writer out = new OutputStreamWriter(new FileOutputStream(modelFile))) {
            template.process(modelMap, out);
            out.flush();
            System.out.printf("INFO: File %s is generated.\n", modelFile.getPath());
        }
    }

    private static String getEntityAttrType(ResultSetMetaData metaData, int columnIndex) throws SQLException {
        String columnName = metaData.getColumnName(columnIndex).toLowerCase();
        String columnType = metaData.getColumnTypeName(columnIndex).toLowerCase();
        int columnSize = metaData.getColumnDisplaySize(columnIndex);
        int columnScale = metaData.getScale(columnIndex);
        
        // 如果列类型是 >= 10 位的 int，则将 Java 类型设置为 Long
//        if (columnType.startsWith("int") && columnSize >= 10) {
//            return "Long";
//        }
//        System.out.println(columnName + "   "+columnType+"  "+columnSize+" ================");
//        if(columnType.startsWith("int") && columnSize >= 10){
//        	return "Long";
//        }
        if(columnType.startsWith("int")){
            return "Integer";
        }
        // if (columnName.startsWith("is_") || columnType.startsWith("tinyint") && columnSize == 1) {
        // 暂时只将列名前缀是 is_ 的列对应的属性设置为 Boolean 型
        if (columnName.startsWith("is_")) {
            return "Boolean";
        }

        if ("decimal".equals(columnType)) {
            if (columnScale > 0) {
                return "Double";
            } else if (columnSize >= 10) {
                return "Long";
            }
        }

        return COLUMN_TYPE_2_ATTR_TYPE_MAPPINGS.get(columnType);
    }

    /**
     * 根据表列名获取 entity 属性名。如果是主键列，则会返回 id，否则返回去掉下划线后的驼峰名，例如：user_id => userId。
     */
    private static String getEntityAttrName(String columnName, boolean isPKColumn) {
        if (isPKColumn) {
            return "id";
        }

        return StringUtils.uncapitalize(getCamelStyleName(columnName));
    }

    /**
     * 根据表名获取 entity 类名。对于需要忽略的表前缀，在生成的 entity 名中不会出现。
     */
    private static String getEntityClassName(String tableName, AppSettings settings) {
        String nameWithoutPrefix = tableName.replaceFirst(settings.getTableIgnorePrefix(), StringUtils.EMPTY);
        return getCamelStyleName(nameWithoutPrefix);
    }

    /**
     * 根据表名获取 dao 类名。对于需要忽略的表前缀，在生成的 dao 名中不会出现。
     */
    private static String getDaoClassName(String tableName, AppSettings settings) {
        return getEntityClassName(tableName, settings) + "Dao";
    }

    /**
     * 将下划线风格的字符串转换为驼峰风格的字符串。
     */
    private static String getCamelStyleName(String snakeStyleName) {
        StringBuilder sb = new StringBuilder();
        String[] strs = snakeStyleName.split("_");
        for (String str : strs) {
            sb.append(StringUtils.capitalize(str));
        }
        return sb.toString();
    }

}
