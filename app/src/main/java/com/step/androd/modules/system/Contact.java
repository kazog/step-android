package com.step.androd.modules.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Create By: Meng
 * Create Date: 21/10/21
 * Desc:
 */
public class Contact {

    public void getContact(Activity context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        context.startActivityForResult(intent, 9097);

//        if(requestCode == 9097 && data != null) {
//            Uri uri = data.getData();
//            String phone = null;
//            String name = null;
//            // 创建内容解析者
//            ContentResolver contentResolver = getContentResolver();
//            Cursor cursor = null;
//            if (uri != null) {
//                cursor = contentResolver.query(uri,
//                        new String[]{"display_name","data1"},null,null,null);
//            }
//            while (cursor.moveToNext()) {
//                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            }
//            cursor.close();
//            //  把电话号码中的  -  符号 替换成空格
//            Log.i("MainActivity", name + phone);
//        }
    }

    public static ArrayList<User> getAllContacts(Context context) {
        ArrayList<User> contacts = new ArrayList<User>();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            User temp = new User();
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            temp.setName(name);

            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                temp.setPhone(phone);
            }

            //获取联系人备注信息
            String note = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_LABEL));
            temp.setNote(note);
            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
        }
        cursor.close();
        return contacts;
    }

    public static class User {
        private String name;
        private String phone;
        private String note;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", note='" + note + '\'' +
                    '}';
        }

    }
}
