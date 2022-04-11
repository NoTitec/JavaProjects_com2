package persistence;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import persistence.mapper.AppliedregistMapper;
import persistence.mapper.CreatedsubjectMapper;
import persistence.mapper.ManagerMapper;
import persistence.mapper.ProfessorMapper;
import persistence.mapper.StudentMapper;
import persistence.mapper.SubjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

public class MybatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    static {//main메모리에 올라감 new 필요없음
        try {
            String resource = "config/config.xml";
            Reader reader = Resources.getResourceAsReader(resource);//설정정보 가져옴
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");//config.xml 정보중 development enviroment정보로 로드
                Class[] mappers={
                        AppliedregistMapper.class,
                        CreatedsubjectMapper.class,
                        ManagerMapper.class,
                        ProfessorMapper.class,
                        StudentMapper.class,
                        SubjectMapper.class
                        //StudentMapper.class ...etc
                };
                for(Class mapper:mappers){
                    sqlSessionFactory.getConfiguration().addMapper(mapper);
                }
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}