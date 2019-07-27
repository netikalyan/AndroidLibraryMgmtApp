package com.netikalyan.librarymanagement.data;

public interface IMemberManagement {
    void addMember(MemberEntity member);

    MemberEntity searchMember(int memberID);

    void modifyMember(MemberEntity member);

    void deleteMember(MemberEntity member);
}
