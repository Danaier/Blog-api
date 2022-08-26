package dye.blogapi.service;

import dye.blogapi.common.CommonResponse;
import dye.blogapi.domain.Blog;

import java.util.List;

public interface BlogService {

    CommonResponse<Blog> getBlogByFilename(String filename);

    CommonResponse<Object> addBlogByUsernameAndfilename(String username,String filename);

    CommonResponse<Object> updateBlogByFilename(String filename);

    CommonResponse<List<String>> getBlogListByUsername(String username);

    CommonResponse<List<String>> searchBlogList(String keyword);


}
