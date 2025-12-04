package cloud.heguangwu.system.config;

import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//自定义字段类型加密处理
@Slf4j
//@Component 加入该注解所有string参数都会进行加解密
@MappedJdbcTypes(JdbcType.VARCHAR)
//@MappedTypes(String.class) 加入该注解所有string参数都会进行加解密
public class EncryptTypeHandler extends BaseTypeHandler<String> {
    private static final String ENCRYPT_KEY = "hello_1234@WORLD";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null) {
            ps.setString(i, AES.encrypt(parameter, ENCRYPT_KEY));
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String password = rs.getString(columnName);
        if(password == null){
            return null;
        }
        log.info("{}加密后的字符串：{}", columnName, password);
        return AES.decrypt(password, ENCRYPT_KEY);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String password = rs.getString(columnIndex);
        if(password == null){
            return null;
        }
        return AES.decrypt(password, ENCRYPT_KEY);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String password = cs.getString(columnIndex);
        if(password == null){
            return null;
        }
        return AES.decrypt(password, ENCRYPT_KEY);
    }
}
