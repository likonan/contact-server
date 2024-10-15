package com.swpu.contactserver.contact.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.contactserver.contact.dto.ContactDTO;
import com.swpu.contactserver.contact.dto.ContactPageQueryDTO;
import com.swpu.contactserver.contact.entity.Contact;
import com.swpu.contactserver.contact.mapper.ContactMapper;
import com.swpu.contactserver.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public void add(ContactDTO contactDTO) {
        contactMapper.insert(contactDTO);
    }


    @Override
    public void updateContact(Contact contact) {
        contactMapper.updateById(contact);
    }

    @Override
    public void delete(Integer id) {
        contactMapper.delete(id);
    }

    @Override
    public Page<Contact> getPageContact(ContactPageQueryDTO contactPageQueryDTO) {
        //初始化page条件
        Page<Contact> page = new Page<>(contactPageQueryDTO.getPage(),contactPageQueryDTO.getPageSize());
        //初始化条件
        QueryWrapper<Contact> w = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(contactPageQueryDTO.getName())){
            w.like("name", contactPageQueryDTO.getName());
        }

        //根据page对象进行分页查询
        return this.page(page,w);
    }
}
