package dye.blogapi.service.impl;

import dye.blogapi.common.CommonResponse;
import dye.blogapi.domain.Blog;
import dye.blogapi.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Override
    public CommonResponse<Blog> getBlogByFilename(String filename) {
        
        return null;
    }

    @Override
    public CommonResponse<Object> addBlogByUsernameAndfilename(String username, String filename) {
        return null;
    }

    @Override
    public CommonResponse<Object> updateBlogByFilename(String filename) {
        return null;
    }

    @Override
    public CommonResponse<List<String>> getBlogListByUsername(String username) {
        return null;
    }

    @Override
    public CommonResponse<List<String>> searchBlogList(String keyword) {
        return null;
    }
}
