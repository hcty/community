package file.majing.community.service;
/**
 * Created by hechuan on 2019/7/31;
 */

import file.majing.community.dto.PaginationDTO;
import file.majing.community.dto.QuestionDTO;
import file.majing.community.dto.QuestionQueryDTO;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import file.majing.community.mapper.QuestionExtMapper;
import file.majing.community.mapper.QuestionMapper;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.Question;
import file.majing.community.model.QuestionExample;
import file.majing.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service public class QuestionService {
	@Autowired private UserMapper userMapper;
	@Autowired private QuestionMapper questionMapper;
	@Autowired private QuestionExtMapper questionExtMapper;

	/**
	 * 首页展示问题
	 * @Author: hechuan on 2019/8/7 22:28
	 * @param: [page, size]
	 * @return: file.majing.community.dto.PaginationDTO
	 */
	public PaginationDTO list(Integer page, Integer size,String search) {
		if(StringUtils.hasLength(search)){
			search = Arrays.stream(search.split(" ")).collect(Collectors.joining("|"));
		}
		PaginationDTO<QuestionDTO> pagination = new PaginationDTO<>();
		QuestionExample questionExample = new QuestionExample();
		QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
		questionQueryDTO.setSearch(search);
		Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		questionExample.setOrderByClause("GMT_CREATE desc");
		questionQueryDTO.setPage(offset);
		questionQueryDTO.setSize(size);
		List<Question> questions = questionExtMapper
				.selectBySearch(questionQueryDTO);
		List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;
		User user = null;
		for (Question question : questions) {
			user = userMapper.selectByPrimaryKey(question.getCreator());
			questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setData(questionDTOS);
		return pagination;
	}

	/**
	 * 展示当前用户问题，我的问题
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public PaginationDTO list(Long userId, Integer page, Integer size) {
		PaginationDTO<QuestionDTO> pagination = new PaginationDTO<>();
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andCreatorEqualTo(userId);
		Integer totalCount = (int) questionMapper.countByExample(questionExample);
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		questionExample.setOrderByClause("GMT_MODIFIED desc");
		List<Question> questions = questionMapper
				.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;
		User user = null;
		for (Question question : questions) {
			user = userMapper.selectByPrimaryKey(question.getCreator());
			questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setData(questionDTOS);
		return pagination;
	}

	/**
	 * 根据id查询问题
	 * @param id
	 * @return
	 */
	public QuestionDTO getById(Long id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		questionDTO.setUser(user);
		return questionDTO;
	}

	/**
	 * 创建或者更新问题
	 *
	 * @Author: hechuan on 2019/9/3 21:20
	 * @param:
	 * @return:
	 */
	@Transactional public void createOrUpdate(Question question) {
		if (question.getId() == null) {
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			question.setViewCount(0);
			question.setCommentCount(0);
			question.setLikeCount(0);
			questionMapper.insert(question);
		} else {
			question.setGmtModified(System.currentTimeMillis());
			QuestionExample questionExample = new QuestionExample();
			questionExample.createCriteria().andIdEqualTo(question.getId());
			int updated = questionMapper.updateByExampleSelective(question, questionExample);
			if (updated != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}

	/**
	 * 修改阅读数
	 *
	 * @Author: hechuan on 2019/9/4 22:40
	 * @param:
	 * @return:
	 */
	public void incView(Long id) {
		Question qestion = new Question();
		qestion.setViewCount(1);
		qestion.setId(id);
		questionExtMapper.incView(qestion);//修改阅读数
	}

	/**
	 * 相关问题展示，查询与该问题标签相关的问题
	 * @param questionDTO
	 * @return 将Question转换成QuestionDTO
	 */
	public List<QuestionDTO> selectReleted(QuestionDTO questionDTO) {
		if (!StringUtils.hasLength(questionDTO.getTag())) {
			return new ArrayList<>();
		}
		//String replaceTag= questionDTO.getTag().replaceAll(",", "|");
		String replaceTag = Arrays.stream(questionDTO.getTag().split(",")).collect(Collectors.joining("|"));
		Question question = new Question();
		question.setId(questionDTO.getId());
		question.setTag(replaceTag);
		List<Question> list = questionExtMapper.selectRelated(question);//mybatis正则匹配查询
		return list.stream().map(q -> {
			QuestionDTO dto = new QuestionDTO();
			BeanUtils.copyProperties(q, dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
