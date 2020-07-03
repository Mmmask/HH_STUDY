package main.sys;

import main.mapper.AbstractResumeMapper;
import main.mapper.MemoryResumeMapper;
import main.entity.AbstractResume;
import main.entity.Resume;
import main.sys.interfaces.HRApplication;

import java.util.Scanner;

/**
 * @author: hqweay
 * @description: TODO
 * Created on 7/2/20 1:13 PM
 */
public abstract class AbstractHRApplication implements HRApplication {
  Scanner scanner = new Scanner(System.in);
  AbstractResumeMapper resumeMapper = new MemoryResumeMapper();
  private boolean exitFlag = false;

  public void run(){
    while(true){
      welcome();
      userOperate();
      if(exitFlag == true){
        System.out.println("====退出");
        break;
      }
    }
  }


  void welcome(){
    System.out.println("===============HR 管理系统================");
    System.out.println("1. 简历列表");
    System.out.println("2. 查询简历");
    System.out.println("3. 添加简历");
    System.out.println("4. 修改简历");
    System.out.println("5. 删除简历");
    System.out.println("===============HR 管理系统================");
  }
  void userOperate(){
    int choice = scanner.nextInt();
    // 接受 nextInt 后的换行符
    scanner.nextLine();
    switch (choice){
      case 1:
        listResume();
        break;
      case 2:
        getResume();
        break;
      case 3:
        saveResume();
        break;
      case 4:
        updateResume();
        break;
      case 5:
        removeResume();
        break;
      default:
        System.out.println("输入错误");
        scanner.close();
        exitFlag = true;
        break;
    }
  }

  @Override
  public void saveResume() {
    System.out.println("====输入姓名");
    String name = scanner.nextLine();

    System.out.println("====输入身份证号");
    String id = scanner.nextLine();

    System.out.println("====输如学校名");
    String school = scanner.nextLine();

    Resume resume = new Resume();
    resume.setName(name);
    resume.setId(id);
    resume.setSchool(school);

    if(resumeMapper.saveResume(resume)){
      System.out.println("success====保存成功");
    }else{
      System.out.println("error====保存失败，可能数据已经存在了～");
    }
  }

  @Override
  public void removeResume() {

  }

  @Override
  public void updateResume() {
    System.out.println("====输入需要修改的简历的身份证号");
    String id = scanner.nextLine();
    Resume resume = new Resume();
    resume.setId(id);

    AbstractResume oldResume =  resumeMapper.getResume(resume);
    if(oldResume == null){
      System.out.println("====没有该用户的简历");
      return;
    }

    System.out.println("====输入姓名，留空则不变。");
    String newName = scanner.nextLine();
    newName = newName.equals("")?oldResume.getName():newName;

    System.out.println("====输入身份证号，留空则不变。");
    String newId = scanner.nextLine();
    newId = newId.equals("")?oldResume.getId():newId;

    System.out.println("====输如学校名，留空则不变。");
    String newSchool = scanner.nextLine();
    newSchool = newSchool.equals("")?oldResume.getSchool():newSchool;

    Resume newResume = new Resume();
    newResume.setName(newName);
    newResume.setId(newId);
    newResume.setSchool(newSchool);

    if(resumeMapper.updateResume(oldResume, newResume)){
      System.out.println("====修改成功");
    }else{
      System.out.println("====修改失败");
    }
  }

  @Override
  public void getResume() {
    System.out.println("====输入身份证号");
    String id = scanner.nextLine();
    Resume resume = new Resume();
    resume.setId(id);

    AbstractResume newResume =  resumeMapper.getResume(resume);
    if(newResume == null){
      System.out.println("====没查到");
    }else{
      System.out.println(newResume.toString());
    }
  }

  @Override
  public void listResume() {
    if(resumeMapper.listResume().size() == 0){
      System.out.println("error====还没有数据");
    }
    System.out.println("====姓名====身份证号====学校====应聘流程");
    for(AbstractResume resume : resumeMapper.listResume()){
      System.out.println(resume.toString());
    }
  }
}
