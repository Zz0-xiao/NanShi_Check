package com.xiao.nanshi_check;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import com.xiao.nanshi_check.db.InspectionDevice;
import com.xiao.nanshi_check.db.dao.InspectionDeviceDao;
import com.xiao.nanshi_check.model.EquipmentBean;

import java.util.List;
import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
//    private Context context;

    public ApplicationTest() {
        super(Application.class);
    }

    //    public void test(){
//        InspectionDevice ind =new InspectionDevice(getContext());
//        SQLiteDatabase db = ind.getWritableDatabase();
//    }
//    @Override
//    protected void setUp() throws Exception {
//        context = getContext();
//        super.setUp();
//    }
//

    /**
     * 测试添加
     *
     * @throws Exception
     */
    public void testAdd() throws Exception {

        InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
        for (long i = 0; i < 100; i++) {
            long number = i;
            dao.add("192.168.0." + number, "x62机床" + number);
        }
        boolean result = dao.add("192.168.0.16", "x62机床");
        assertEquals(true, result);

//        InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
//        Random random = new Random(8979);
//        for (long i = 1; i < 5; i++) {
//            long number = 13500000000l + i;
//            dao.add(number + "", String.valueOf(random.nextInt(3) + 1));
//        }
//      boolean result = dao.add("13500000000", "1");
//      assertEquals(true, result);
    }
//
//    /**
//     * 测试删除
//     *
//     * @throws Exception
//     */
//    public void testDelete() throws Exception {
//        InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
//        boolean result = dao.delete("13500000003");
//        assertEquals(true, result);
//    }
//

    /**
     * 删除全部
     *
     * @throws Exception
     */

    public void testAllDelete() throws Exception {
        InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
        boolean result = dao.deleteAll();
        assertEquals(true, result);
    }
//
////    /**
////     * 测试修改
////     *
////     * @throws Exception
////     */
////    public void testUpdate() throws Exception {
////        BlackNumberDao dao = new BlackNumberDao(context);
////        boolean result = dao.changeBlockMode("13500000000", "2");
////        assertEquals(true, result);
////    }
////
////    /**
////     * 测试查询
////     *
////     * @throws Exception
////     */
////    public void testFind() throws Exception {
////        BlackNumberDao dao = new BlackNumberDao(context);
////        String mode = dao.findBlockMode("13500000000");
////        System.out.println(mode);
////    }
////
//
//    /**
//     * 测试查询全部
//     *
//     * @throws Exception
//     */
//    public void testFindAll() throws Exception {
//        InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
//        List<EquipmentBean> ebs = dao.findAll();
//        for (EquipmentBean eb : ebs) {
//            System.out.println(eb.getEquipmentIp() + "---" + eb.getEquipmentName());
//        }
//    }
}