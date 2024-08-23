package org.example.librarysystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.librarysystem.dto.BookInfoDTO;
import org.example.librarysystem.entity.BookInfo;

public interface BookInfoService extends IService<BookInfo> {
    // 添加书籍信息
    boolean addBookInfo(BookInfo bookInfo);

    // 删除书籍信息
    boolean deleteBookInfo(String id);

    // 更新书籍信息
    boolean updateBookInfo(BookInfo bookInfo);

    // 根据id查询书籍信息
    BookInfo getBookInfoById(String id);
    // 分页查询书籍信息
    Page<BookInfo> getBookInfoPage(BookInfoDTO bookInfoDTO);
}
