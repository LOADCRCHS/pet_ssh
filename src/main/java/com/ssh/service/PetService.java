package com.ssh.service;

import com.ssh.pojo.ConsumeRecordTO;
import com.ssh.pojo.MyPageInfo;
import com.ssh.pojo.PageInfo;
import com.ssh.pojo.PetTO;

public interface PetService {
    MyPageInfo<PetTO> getAllPet(Integer pageNum, Integer pageSize, PetTO criteria);

    MyPageInfo<ConsumeRecordTO> getConsumeRecordByPetId(Integer pageNum, Integer pageSize, ConsumeRecordTO consumeRecord);

    long getPetCount();

    void addConsumeRecord(ConsumeRecordTO consumeRecord);

    void addPet(PetTO pet);

    void updatePet(PetTO pet);
}
