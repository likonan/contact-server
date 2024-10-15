package com.swpu.contactserver.contact.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swpu.contactserver.contact.dto.ContactDTO;
import com.swpu.contactserver.contact.dto.ContactPageQueryDTO;
import com.swpu.contactserver.contact.entity.Contact;

public interface ContactService extends IService<Contact> {
    void add(ContactDTO contactDTO);

    void updateContact(Contact contact);

    void delete(Integer id);

    Page<Contact> getPageContact(ContactPageQueryDTO contactPageQueryDTO);
}
