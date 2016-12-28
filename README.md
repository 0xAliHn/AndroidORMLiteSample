## ORMLite Library for android

### What is Ormlite?

It is an object relational mapping framework, which provides a set of simple lightweight functionalities to persist java objects to SQL databases, while alleviating the developer of the complexity and overhead of writing complex SQL queries.

```java
@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(canBeNull = false, columnName = "desc")
    private String desc;

    public User() {}

    public User(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
```

### OrmLiteSqliteOpenHelper：
```java
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "demo.db";

    private static Context mApplicationContext;

    private static DatabaseHelper instance;

    private Map<String, Dao> daoMaps = new HashMap<>();

    private DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Article.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Article.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initOrmLite(Context context) {
        mApplicationContext = context;
        getInstance();
    }

 
    public static DatabaseHelper getInstance() {
        if (instance == null) {
            synInit(mApplicationContext);
        }
        return instance;
    }

    private synchronized static void synInit(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao;
        String className = clazz.getSimpleName();

        if (daoMaps.containsKey(className)) {
            dao = daoMaps.get(className);
        } else {
            dao = super.getDao(clazz);
            daoMaps.put(className, dao);
        }
        return dao;
    }


    @Override
    public void close() {
        super.close();
        for (String key : daoMaps.keySet()) {
            Dao dao = daoMaps.get(key);
            dao = null;
        }
    }

}
```

```java
public class UserDao {

    public static UserDao mUserDaoInstance;

    private Dao<User, Integer> mUserDao;

    public UserDao() {
        try {
            mUserDao = DatabaseHelper.getInstance().getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDao getInstance() {
        if (mUserDaoInstance == null) {
            mUserDaoInstance = new UserDao();
        }
        return mUserDaoInstance;
    }


    public void insertUser(User user) {
        try {
            mUserDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 
    public void insertUsers(List<User> users) {
        try {
            mUserDao.create(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> queryAllUser() {
        List<User> users = new ArrayList<>();
        try {
            users = mUserDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public User queryUserById(int id) {
        User user = null;
        try {
            user = mUserDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public void deleteUserById(int id) {
        try {
            mUserDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteUserByIds(List<Integer> ids) {
        try {
            mUserDao.deleteIds(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAllUser() {
        try {
            mUserDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUser(User user) {
        try {
            mUserDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 
    public void updateUserById(User user, int id) {
        try {
            mUserDao.updateId(user, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> queryBy() throws SQLException {
        QueryBuilder<User, Integer> queryBuilder = mUserDao
                .queryBuilder();
        Where<User, Integer> where = queryBuilder.where();
        where.eq("id", 1);
        where.and();
        where.eq("name", "xxx");

        //或者
        mUserDao.queryBuilder().
                where().
                eq("id", 1).and().
                eq("name", "xxx");
        return queryBuilder.query();
    }
}
```

