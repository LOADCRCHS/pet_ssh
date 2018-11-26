package com.ssh.service.impl;

import com.ssh.dao.PetDao;
import com.ssh.pojo.ConsumeRecordTO;
import com.ssh.pojo.MyPageInfo;
import com.ssh.pojo.PageInfo;
import com.ssh.pojo.PetTO;
import com.ssh.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetDao petDao;

    @Override
    public MyPageInfo<PetTO> getAllPet(Integer pageNum, Integer pageSize, PetTO criteria) {
        return petDao.getPetByPage(pageNum, pageSize, criteria);
    }

    @Override
    public MyPageInfo<ConsumeRecordTO> getConsumeRecordByPetId(Integer pageNum, Integer pageSize, ConsumeRecordTO consumeRecord) {
        return petDao.getConsumeRecordTOByPetTOId(pageNum, pageSize, consumeRecord);
    }

    @Override
    public long getPetCount() {
        return petDao.getPetCount();
    }

    @Override
    public void addConsumeRecord(ConsumeRecordTO consumeRecord) {
        double balance = petDao.getBalanceByPetTOId(consumeRecord.getPetId());
        if (consumeRecord.getConsumeType() == 0) {
            petDao.changeBalance(consumeRecord.getConsumeMoney() + balance, consumeRecord.getPetId());
        } else {
            //当消费金额大于余额时，余额不会改变
            if (consumeRecord.getConsumeMoney() > balance) {
                consumeRecord.setRemark("余额不足，消费失败");
                petDao.addConsumeRecordTO(consumeRecord);
                return;
            }
            consumeRecord.setConsumeMoney(consumeRecord.getConsumeMoney());
            petDao.changeBalance(balance - consumeRecord.getConsumeMoney(), consumeRecord.getPetId());
        }
        double afterBalance = petDao.getBalanceByPetTOId(consumeRecord.getPetId());
        consumeRecord.setBalance(afterBalance);
        petDao.addConsumeRecordTO(consumeRecord);
    }

    @Override
    public void addPet(PetTO pet) {
        petDao.addOrUpdatePet(pet);
    }

    @Override
    public void updatePet(PetTO pet) {
        petDao.addOrUpdatePet(pet);
    }
}
