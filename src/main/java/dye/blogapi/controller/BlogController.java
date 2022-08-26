package dye.blogapi.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//使用Rest前缀就不用在方法上加ResponseBody
@RequestMapping("/blog/")
@Api(tags = "博客文章相关接口")
public class BlogController {



}
