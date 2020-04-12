package file.majing.community.mapper;
/**
 * 自定义Mapper,扩展generator插件生成方法的不足
 */
import file.majing.community.model.Comment;
public interface CommentExtMapper {
    /**
     * 增加评论回复数
     * @param comment
     * @return
     */
    int incCommentCount(Comment comment);
}