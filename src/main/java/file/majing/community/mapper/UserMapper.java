package file.majing.community.mapper;

import file.majing.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	/**
	 * 添加用户
	 * @param user
	 */
	@Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},"
			+ "#{accountId},#{token},#{gmtCreate},#{gmtModified})") void insert(User user);

}
