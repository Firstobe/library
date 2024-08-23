package org.example.librarysystem.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.example.librarysystem.dto.BookInfoDTO;
import org.example.librarysystem.entity.BookInfo;
import org.example.librarysystem.service.BookInfoService;
import org.example.librarysystem.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library-service/bookInfo")
@Slf4j
@Schema(description = "图书信息")
public class BookInfoController {
    /*
     * @Author cms
     * @Description //TODO $
     * @Date $ $
     * @Param $
     * @return $
     **/
    @Autowired
    private BookInfoService bookInfoService;
    /**
     * 新增图书信息
     */
    @PostMapping("")
    public Result addBookInfo(@RequestBody BookInfoDTO bookInfoDTO) {
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(bookInfoDTO, bookInfo);
        if (bookInfoService.addBookInfo(bookInfo)){
            return Result.succ("新增图书信息成功");
        }else {
            return Result.fail("新增图书信息失败");
        }
    }
    /**
     * 删除图书信息
     */
    @DeleteMapping("")
    public Result deleteBookInfo(@RequestBody BookInfoDTO bookInfoDTO) {
        if ( bookInfoService.deleteBookInfo(bookInfoDTO.getId())){
            return Result.succ("删除图书信息成功");
        }else {
            return Result.fail("删除图书信息失败");
        }
    }
    /**
     * 修改图书信息
     */
    @PutMapping("")
    public Result updateBookInfo(@RequestBody BookInfoDTO bookInfoDTO) {
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(bookInfoDTO, bookInfo);
        if (bookInfoService.updateBookInfo(bookInfo)){
            return Result.succ("修改图书信息成功");
        }else {
            return Result.fail("修改图书信息失败");
        }
    }
    /**
     * 分页查询图书信息
     */
    @GetMapping("")
    public Result getBookInfoPage(BookInfoDTO bookInfoDTO) {
        return Result.succ(bookInfoService.getBookInfoPage(bookInfoDTO));
    }
}
