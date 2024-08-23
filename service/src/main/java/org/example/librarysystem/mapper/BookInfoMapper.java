package org.example.librarysystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.librarysystem.entity.BookInfo;
@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
}
