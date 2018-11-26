package com.ssh.action;

import com.opensymphony.xwork2.Action;
import com.ssh.pojo.ConsumeRecordTO;
import com.ssh.pojo.MyPageInfo;
import com.ssh.pojo.PetTO;
import com.ssh.pojo.TableData;
import com.ssh.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;

public class PetAction implements Action {
    @Autowired
    private PetService petService;

    private TableData tableData = new TableData();

    private PetTO pet;

    private int page;

    private int limit;

    private int petId;

    public String index() {
        return SUCCESS;
    }

    public String pet() {
        return SUCCESS;
    }


    public String getAllRecord() {
        if (petId==0) {
            return ERROR;
        }
        ConsumeRecordTO consumeRecordTO = new ConsumeRecordTO();
        MyPageInfo<ConsumeRecordTO> allConsumeRecord = petService.getConsumeRecordByPetId(page, limit, consumeRecordTO);

        tableData.setCode(0);
        tableData.setCount(allConsumeRecord.getTotalCount());
        tableData.setData(allConsumeRecord.getList());

        return SUCCESS;
    }

    /**
     * 获取所有宠物列表
     */
    public String getAllPet() {

        MyPageInfo<PetTO> allPet = petService.getAllPet(page, limit, pet);

        tableData.setCode(0);
        tableData.setCount(allPet.getTotalCount());
        tableData.setData(allPet.getList());

        return SUCCESS;
    }


    @Override
    public String execute() throws Exception {
        return null;
    }

    public TableData getTableData() {
        return tableData;
    }

    public PetTO getPet() {
        return pet;
    }

    public void setPet(PetTO pet) {
        this.pet = pet == null ? new PetTO() : pet;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page == 0 ? 1 : page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit == 0 ? 2 : limit;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }
}
