package file.majing.community.mapper;

import file.majing.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper public interface UserMapper {
	/**
	 * 添加用户
	 *
	 * @param user
	 */
	@Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values (#{name},"
			+ "#{accountId},#{token},#{gmtCreate},#{gmtModified},#{portrait},#{avatarUrl})") void insert(User user);

	/**
	 * 按照token查询user
	 * @param token
	 * @return
	 */
	@Select("select * from user where token = #{token}") User findByToken(@Param("token") String token);

	@Select("select * from user where id = #{id}")User findById(@Param("id") Integer id);
}
