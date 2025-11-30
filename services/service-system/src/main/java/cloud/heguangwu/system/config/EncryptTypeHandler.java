package cloud.heguangwu.system.config;

import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.val;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//自定义字段类型加密处理
@Component
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String.class)
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
