package com.swpu.contactserver.contact.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.contactserver.contact.dto.ContactDTO;
import com.swpu.contactserver.contact.entity.Contact;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContactMapper extends BaseMapper<Contact> {

    @Insert("INSERT INTO contact (name,address,phone) VALUES (#{name},#{address},#{phone})")
    void insert(ContactDTO contactDTO);
    @Select("SELECT * FROM contact")
    List<Contact> list();

    void update(Contact contact);

    @Delete("DELETE FROM contact WHERE id=#{id}")
    void delete(Integer id);
}
