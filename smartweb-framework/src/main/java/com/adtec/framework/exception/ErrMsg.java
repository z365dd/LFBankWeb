/**
 * 系统名称: SmartWeb平台
 * 模块名称: 异常模块
 * 功能描述: 错误信息类
 * 类 名 称  : ErrMsg.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月18日 上午11:02:33<br>
 * 系统版本: V1.0.0<br>
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * <p>
 * ========     ======  ============================================
 */
package com.adtec.framework.exception;

import org.apache.axis.utils.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author chenyl
 *
 */
public class ErrMsg {
    public final static Map<String, String> ERR_MSG = new HashMap();

    static {
        ERR_MSG.put("EXIT_CODE", "EXIT_CODE");
        ERR_MSG.put("0000", "交易成功");
        ERR_MSG.put("0001", "部分成功");
        ERR_MSG.put("0002", "全部失败");
        ERR_MSG.put("0003", "字段[%s]输入为空");
        ERR_MSG.put("0004", "[%s]数据不存在");
        ERR_MSG.put("0005", "结果集转换为树型结构异常");
        ERR_MSG.put("0006", "json转换失败异常");
        ERR_MSG.put("0007", "空指针异常");
        ERR_MSG.put("0008", "不支持的错误");
        ERR_MSG.put("0009", "io读取错误异常");
        ERR_MSG.put("0010", "没有设置TemplateLoader异常");
        ERR_MSG.put("0011", "公共窗体name属性为空异常");

        /*Dataset数据集中的错误*/
        ERR_MSG.put("0065", "行索引超出范围");
        ERR_MSG.put("0066", "列索引超出范围");
        ERR_MSG.put("0067", "指定的行列位置无效");
        ERR_MSG.put("0068", "列名不存在");
        ERR_MSG.put("0069", "列类型无效");
        ERR_MSG.put("0070", "IDataset中尽力的类型转换失败");

        ERR_MSG.put("EXIT_CODE", "EXIT_CODE");
        ERR_MSG.put("13330", "装载版本清单文件当前版本[%s]失败");
        ERR_MSG.put("12109", "计算机器名称失败,预期机器[%s]未开启子系统内部路由！");
        ERR_MSG.put("12108", "计算机器名称失败,预期机器[%s]未启动！");
        ERR_MSG.put("12107", "计算机器名称失败,[%s]");
        ERR_MSG.put("12106", "计算机器名称失败,预期机器名[%s]未找到！");
        ERR_MSG.put("12105", "计算路由入口失败,[%s]下未找到合适的路由入口！");
        ERR_MSG.put("12104", "计算路由入口失败,[%s]下未定义路由入口！");
        ERR_MSG.put("12103", "计算响应路由失败,[%s]下未找到合适的路由！");
        ERR_MSG.put("12102", "计算响应路由失败,[%s]下未定义响应路由！");
        ERR_MSG.put("12101", "计算请求路由失败,[%s]下未找到合适的路由！");
        ERR_MSG.put("12100", "计算请求路由失败,[%s]下未定义请求路由！");
        ERR_MSG.put("13329", "装载版本清单文件[%s]失败");
        ERR_MSG.put("13328", "创建版本清单文件[%s]失败");
        ERR_MSG.put("13327", "从存储map[%s]中取此key[%s]映射的值不存在");
        ERR_MSG.put("13326", "资源版本号不能为空");
        ERR_MSG.put("13325", "资源池中无此[%s]类型资源");
        ERR_MSG.put("13324", "资源名称不能为空或者空串");
        ERR_MSG.put("13323", "文件[%s]中[%s]结点的子结点[%s]的结点属性[%s]值重复");
        ERR_MSG.put("13322", "数据元素[%s]中引用的数据字典[%s]不存在");
        ERR_MSG.put("13321", "当数据元素为[%s]类型时，子节点记录数只能为1");
        ERR_MSG.put("10209", "调用方法[%s]失败");
        ERR_MSG.put("10402", "对象类型转换从[%s]To[%s]出错，原字符串为[%s]");
        ERR_MSG.put("13320", "资源版本号[%s]不存在");
        ERR_MSG.put("10208", "调用方法[%s]失败");
        ERR_MSG.put("10401", "日期格式转换出错，日期值为[%s],原格式为[%s],目标格式为[%s]");
        ERR_MSG.put("10207", "在类[%s]中查找方法[%s]失败");
        ERR_MSG.put("10400", "日期格式转换出错，日期格式为[%s]，日期值为[%s]");
        ERR_MSG.put("10206", "装载类[%s]失败");
        ERR_MSG.put("10205", "无效的类名或方法名[%s]");
        ERR_MSG.put("10204", "字符型数据参与了减、乘、除运算!");
        ERR_MSG.put("10203", "表达式不支持此对象类型[%s]的计算处理");
        ERR_MSG.put("10202", "出栈恢复全局变量失败");
        ERR_MSG.put("10201", "压栈保存全局变量失败");
        ERR_MSG.put("10200", "表达式[%s]计算错误");
        ERR_MSG.put("10004", "错误的配置参数[%s]值/描述[%s]");
        ERR_MSG.put("10003", "索引[%s]数值异常");
        ERR_MSG.put("10002", "描述数据长度[%d]超出最大有效长度[%d]");
        ERR_MSG.put("10001", "对象[%s]为空");
        ERR_MSG.put("10000", "错误信息[%s]");
        ERR_MSG.put("13319", "版本[%s]资源[%s]池无[%s]资源");
        ERR_MSG.put("13318", "资源文件[%s]格式错误");
        ERR_MSG.put("13317", "[%s]业务下无[%s]子业务资源池");
        ERR_MSG.put("13316", "无[%s]业务资源资源池");
        ERR_MSG.put("13315", "[%s]业务[%s]子业务无[%s]资源");
        ERR_MSG.put("13314", "[%s]业务下无[%s]资源");
        ERR_MSG.put("13313", "在资源池根目录下无[%s]资源");
        ERR_MSG.put("13312", "资源类型错误码");
        ERR_MSG.put("13311", "资源名称[%s]错误");
        ERR_MSG.put("13310", "[%s]资源池无此[%s]资源");
        ERR_MSG.put("11219", "统计DTA[%s]取出所有实例计算结果失败");
        ERR_MSG.put("11218", "插件信息，获取DTA[%s]所有实例集合信息失败");
        ERR_MSG.put("13309", "解析的结点为空，无法取[%s]属性或结点");
        ERR_MSG.put("11217", "统计插件信息DTA[%s]失败");
        ERR_MSG.put("13308", "[%s]结点的子结点中，[%s]结点Name属性值重复。");
        ERR_MSG.put("11216", "更新运行参数池的信息DTA[%s]信息失败");
        ERR_MSG.put("13307", "图表分类信息不完整，无法继续解析。");
        ERR_MSG.put("11215", "登记插件信息DTA[%s]中的服务[%s]信息失败");
        ERR_MSG.put("13306", "[%s]文件中无此[%s]资源结点！");
        ERR_MSG.put("11214", "登记插件信息,运行资源池无DTA[%s]信息");
        ERR_MSG.put("13305", "装载[%s]类型资源失败，解析的文件名为[%s]！");
        ERR_MSG.put("11213", "流读写超时");
        ERR_MSG.put("13304", "[%s]文件解析失败，文件中[%s]结点指定的记录数为[%d]，实际中的记录数为[%d]！");
        ERR_MSG.put("11212", "未读到DTA[%s]参数信息");
        ERR_MSG.put("13303", "[%S]文件中有重复结点，结点名称为[%s],重复名称为[%s]！");
        ERR_MSG.put("11211", "源DTA[%s]解析报文组超时");
        ERR_MSG.put("13302", "[%s]文件中[%s]节点的[%s]属性的类型错误，定制的值为[%s]！");
        ERR_MSG.put("11210", "DTA[%s]路由计算失败");
        ERR_MSG.put("13301", "[%s]文件中[%s]结点下不包含[%s]节点！");
        ERR_MSG.put("13300", "要解析的文件名称不能为空！");
        ERR_MSG.put("12199", "计算路由失败,预期DTA[%s]未找到！");
        ERR_MSG.put("11209", "DTA[%s]获取链路失败");
        ERR_MSG.put("11208", "[%]");
        ERR_MSG.put("11207", "反射[%s]对象[%s]方法或属性失败");
        ERR_MSG.put("11206", "DTA[%s]的实例获取链接失败");
        ERR_MSG.put("11205", "DTA[%s]的实例处理任务失败");
        ERR_MSG.put("11204", "DTA[%s]关闭操作失败");
        ERR_MSG.put("11203", "DTA[%s]重启失败");
        ERR_MSG.put("11202", "强制转换[%s]失败");
        ERR_MSG.put("11201", "实例化DTA[%s]对象失败");
        ERR_MSG.put("11007", "报文[%s]组装处理失败");
        ERR_MSG.put("11200", "DTA[%s]启动失败");
        ERR_MSG.put("11006", "错误判断失败");
        ERR_MSG.put("11004", "未定义交易报文[%s]");
        ERR_MSG.put("11003", "名称为[%s]的报文格式不存在");
        ERR_MSG.put("11002", "预期记录数[%d]与实际解析记录数[%d]不符");
        ERR_MSG.put("11001", "预期标识符[%s]未找到");
        ERR_MSG.put("11000", "报文[%s]解析处理失败");
        ERR_MSG.put("10133", "数据元素[%s]中item项为空");
        ERR_MSG.put("10132", "数据元素[%s]中item项[%s]不存在");
        ERR_MSG.put("10131", "数据元素应有[n]");
        ERR_MSG.put("10130", "数据元素[%s]的第[%d]下标的值为空");
        ERR_MSG.put("10129", "数据池解包失败,解析数据元素[%s]失败");
        ERR_MSG.put("10128", "数据元素[%s]类型[%s]与报文中的数据元素类型[%s]不符");
        ERR_MSG.put("10127", "数据池解包失败");
        ERR_MSG.put("10126", "数据池此[%s]数据元素不支持此[%s]数据数据类型赋值");
        ERR_MSG.put("10125", "[%s]数据元素不能跨下标负值");
        ERR_MSG.put("10124", "[%s]数据元素为常量数据元素,值不能修改");
        ERR_MSG.put("10123", "无此数据元素类型[%d]");
        ERR_MSG.put("10122", "[%s][%s]数据类型转换失败,取得的默认值[%s]");
        ERR_MSG.put("10121", "[%s]数据类型与数据元素[%s]的数据类型[%s]不符");
        ERR_MSG.put("10120", "对数据元素[%s]的赋值的长度[%s]超过了最大长度[%s]");
        ERR_MSG.put("10315", "文件[%s]读出错");
        ERR_MSG.put("10314", "向文件[%s]中写入长度[%d]字节的数据失败！");
        ERR_MSG.put("10313", "数据[%s]CRC校验失败");
        ERR_MSG.put("10119", "数据类型不符");
        ERR_MSG.put("10312", "文件[%s]CRC校验失败");
        ERR_MSG.put("10118", "数据元素操作记录未初始化,堆栈号为[%d]");
        ERR_MSG.put("10311", "文件[%s]传输中出现异常，无法确定，请查看日志");
        ERR_MSG.put("10117", "数据元素操作记录堆栈已达上限[%d]");
        ERR_MSG.put("10310", "文件[%s]发送失败");
        ERR_MSG.put("10116", "数据池复制失败");
        ERR_MSG.put("10115", "数据类型转换失败");
        ERR_MSG.put("10114", "此[%s]数据元素的下标值不是数组");
        ERR_MSG.put("10113", "此[%s]数据元素的下标值是数组");
        ERR_MSG.put("10112", "此[%s]数据元素的值是空指针");
        ERR_MSG.put("10111", "无此类类型");
        ERR_MSG.put("10110", "数据池不支持此[%s]数据数据类型负值");
        ERR_MSG.put("12004", "DRQServer启动失败");
        ERR_MSG.put("12003", "内部通讯失败");
        ERR_MSG.put("12002", "DTA并发数+1操作失败");
        ERR_MSG.put("12001", "删除交易登记表失败");
        ERR_MSG.put("12000", "记录交易登记表失败");
        ERR_MSG.put("10309", "文件[%s]接收失败");
        ERR_MSG.put("10308", "文件[%s]读写超时");
        ERR_MSG.put("10307", "文件[%s]写超时");
        ERR_MSG.put("10306", "文件[%s]读取超时");
        ERR_MSG.put("10305", "文件[%s]不能访问");
        ERR_MSG.put("10304", "将数据写入文件[%s]时失败");
        ERR_MSG.put("10303", "读取文件[%s]数据失败");
        ERR_MSG.put("10109", "数据池中此[%s]数据元素无此[%d]下标");
        ERR_MSG.put("10302", "文件[%s]创建失败");
        ERR_MSG.put("10108", "数据池中此[%s]数据元素的值为空");
        ERR_MSG.put("10301", "文件[%s]不存在");
        ERR_MSG.put("10107", "数据池中无此[%s]数据元素");
        ERR_MSG.put("10106", "[%s]元素的值是空的");
        ERR_MSG.put("10105", "[%s]子数据池是空的");
        ERR_MSG.put("10104", "IP数据池已达到满负荷状态");
        ERR_MSG.put("10103", "无此[%s]子数据池类型");
        ERR_MSG.put("10102", "无此[%s]子数据池");
        ERR_MSG.put("10101", "无可用子数据池");
        ERR_MSG.put("10100", "数据元素[%s]不存在");
        ERR_MSG.put("11113", "调用[%s]失败！");
        ERR_MSG.put("11112", "调用[%s]webservice[%s]后，请求数据失败");
        ERR_MSG.put("11111", "调用[%s]webservice[%s]失败");
        ERR_MSG.put("11110", "从[%s]服务器[%d]端口发送数据后接受返回数据失败");
        ERR_MSG.put("11109", "与[%s]服务器进行[%s]通讯，[%s]数据超时");
        ERR_MSG.put("11108", "与[%s]服务器[%d]端口报文通讯,%s");
        ERR_MSG.put("11107", "从[%s]服务器[%d]端口接收报文通讯失败");
        ERR_MSG.put("11106", "连接超时");
        ERR_MSG.put("11105", "从SOCKET中读取长度[%d]字节的数据失败");
        ERR_MSG.put("11104", "从SOCKET中读取长度[%d]字节的数据超时");
        ERR_MSG.put("11103", "连接超时");
        ERR_MSG.put("11102", "接收[%s]文件失败");
        ERR_MSG.put("11101", "主机[%s]不存在");
        ERR_MSG.put("11100", "[报文通讯，%s]");
        ERR_MSG.put("12113", "计算路由入口失败,[%s]入口定义错误！");
        ERR_MSG.put("12112", "计算机器名称失败,预期机器[%s]未启动该服务[%s]！");
        ERR_MSG.put("12111", "计算机器名称失败,预期机器[%s]未启动该DTA[%s]！");
        ERR_MSG.put("12110", "计算机器名称失败,预期机器[%s]未部署该DTA[%s]！");
        ERR_MSG.put("12000", "记录交易登记表失败");
        ERR_MSG.put("12002", "DTA并发数+1操作失败");
        ERR_MSG.put("12003", "内部通讯失败");
        ERR_MSG.put("12004", "DRQServer启动失败");
        ERR_MSG.put("11213", "流读写超时");
        ERR_MSG.put("10132", "数据元素[%s]中item项[%s]不存在");
        ERR_MSG.put("10133", "数据元素[%s]中item项为空");
        ERR_MSG.put("13328", "创建版本清单文件[%s]失败");

        ERR_MSG.put("13329", "装载版本清单文件[%s]失败");
        ERR_MSG.put("13330", "装载版本清单文件当前版本[%s]失败");
        ERR_MSG.put("10134", "数据元素[%s]中下标格式不正确");
        ERR_MSG.put("10135", "数据元素[%s]中的下标与参数中的下标[%s]不符");
        ERR_MSG.put("14101", "当前状态禁止进制执行操作[%s]");
        ERR_MSG.put("14102", "排队中自动任务已超出个数上限，自动任务[%s]触发失败");
        ERR_MSG.put("14103", "调用ALA失败");
        ERR_MSG.put("14104", "ALA[%s]处理结果判定失败");
        ERR_MSG.put("14105", "错误的自动任务参数[%s]定义[%s]");
        ERR_MSG.put("14106", "本节点不支持自动任务[%s]");
        ERR_MSG.put("10320", "启动文件传输服务失败,IP[%s],PORT[%s]已被占用");
        ERR_MSG.put("12005", "DTA并发数-1操作失败");
        ERR_MSG.put("12006", "DTA并发数+1操作，交易优先级[%d]数值不正确");
        ERR_MSG.put("12007", "DTA总并发数超限，DTA[%s]在机器[%s]上的总并发数上限为[%d]，当前并发数为[%d]");
        ERR_MSG.put("12008", "DTA优先级并发数超限，DTA[%s]在机器[%s]上的优先级[%s]并发数上限为[%d]，当前并发数为[%d]");
        ERR_MSG.put("11236", "适配器[%s]下实例号为[%s]的实例处理服务[%s]失败");
    }

    public static String get(String key) {
        return ((String) ERR_MSG.get(key));
    }

    public static HashMap<String, String> init() {
        HashMap err_msg = new HashMap();
        Properties p = new Properties();
        FileInputStream ferr = null;

        String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        try {
            ferr = new FileInputStream(path + "ErrorMessage.properties");
            System.out.println("装载错误码文件--------" + path + "ErrorMessage.properties");
            FileReader fr = null;
            BufferedReader br = null;

            p.load(ferr);
            ferr.close();
            Set s = p.keySet();
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String id = (String) it.next();
                String value = "";
                if (!StringUtils.isEmpty(id)) {
                    value = p.getProperty(id);
                }
                //value = new String(value.getBytes("ISO-8859-1"), "UTF-8");

                err_msg.put(id, value);
                ERR_MSG.put(id, value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (ferr != null) {
                try {
                    ferr.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
        return err_msg;
    }

}
