package org.example.librarysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.librarysystem.dto.BookInfoDTO;
import org.example.librarysystem.entity.BaseEntity;
import org.example.librarysystem.entity.BookInfo;
import org.example.librarysystem.mapper.BookInfoMapper;
import org.example.librarysystem.service.BookInfoService;
import org.example.librarysystem.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Date;

@Slf4j
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {
    /*
     * @Author cms
     * @Description //TODO $
     * @Date $ $
     * @Param $
     * @return $
     **/
    @Override
    @Transactional
    public boolean addBookInfo(BookInfo bookInfo) {
        log.info("新增书籍信息成功。");
       return this.save(bookInfo);
    }

    @Override
    @Transactional
    public boolean deleteBookInfo(String id) {
        try {
            BookInfo bookInfo = this.getById(id);
            if (bookInfo != null){
                bookInfo.setDeleted("1");
                bookInfo.setUpdateTime(new Date());
                bookInfo.setUpdateUser(SecurityUtils.getLoginUserId());
                log.info("id="+ id +"删除书籍信息成功。");
                return this.updateById(bookInfo);
            }else {
                log.error("id="+ id +"删除书籍信息失败。");
                return false;
            }
        }catch (Exception e){
            log.error("id="+ id +"删除书籍信息失败。原因："+e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateBookInfo(BookInfo bookInfo) {
        try {
            BookInfo getBookInfo = this.getById(bookInfo.getId());
            if (getBookInfo != null){
                bookInfo.setUpdateTime(new Date());
                bookInfo.setUpdateUser(SecurityUtils.getLoginUserId());
                log.info("id="+ bookInfo.getId() +"更新书籍信息成功。");
                return this.updateById(bookInfo);
            }else {
                log.error("id="+ bookInfo.getId() +"更新书籍信息成功。");
                return false;
            }
        }catch (Exception e){
            log.error("id="+ bookInfo.getId() +"删除书籍信息失败。原因："+e.getMessage());
            return false;
        }
    }

    @Override
    public BookInfo getBookInfoById(String id) {
        if (id == null){
            return null;
        }
        BookInfo bookInfo = this.getById(id);
        return bookInfo;
    }

    @Override
    public Page<BookInfo> getBookInfoPage(BookInfoDTO bookInfoDTO) {
        int pageNum = bookInfoDTO.getPageNum() == null ? 1 : bookInfoDTO.getPageNum();
        int pageSize = bookInfoDTO.getPageSize() == null ? 10 : bookInfoDTO.getPageSize();
        //查询条件封装
        LambdaQueryWrapper<BookInfo> queryWrapper = buildQueryBookInfo(bookInfoDTO);
        return this.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /**
     * 查询条件封装
     * @param bookInfoDTO
     */
    public static LambdaQueryWrapper<BookInfo> buildQueryBookInfo(BookInfoDTO bookInfoDTO){
        LambdaQueryWrapper<BookInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(bookInfoDTO.getTitle())){
            queryWrapper.like(BookInfo::getTitle, bookInfoDTO.getTitle());
        }
        if (StringUtils.hasLength(bookInfoDTO.getAuthor())){
            queryWrapper.like(BookInfo::getAuthor, bookInfoDTO.getAuthor());
        }
        if (StringUtils.hasLength(bookInfoDTO.getPublisher())){
            queryWrapper.like(BookInfo::getPublisher, bookInfoDTO.getPublisher());
        }
        if (StringUtils.hasLength(bookInfoDTO.getCategory())){
            queryWrapper.like(BookInfo::getCategory, bookInfoDTO.getCategory());
        }
        queryWrapper.eq(BookInfo::getDeleted, "0");
        queryWrapper.orderByDesc(BaseEntity::getCreateTime);
        return queryWrapper;
    }
}
