//
//  User.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/13.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import HandyJSON

class User: HandyJSON {
    
    /** 用户ID */
    var userId:String?
    /** 用户名 */
    var username:String?
    /** 昵称 */
    var nickname:String?
    /** 年龄 */
    var age:Int?
    /** 爱好 */
    var hobbys:[String]?
    /** 用户类型 */
    var userType:UserType?
    /** 居住地 */
    var address:Address?
    /** 注册日期 */
    var registerDate:Date?
    
    required init() {}
    
    func willStartMapping() {
        print("将要进行解析")
    }
  
    func mapping(mapper: HelpingMapper) {
        // key名称不同
        mapper <<<
            self.userId <-- "id"
        
        // 日期
        mapper <<<
            self.registerDate <-- CustomDateFormatTransform(formatString: "yyyy-MM-dd")
    }
    
    func didFinishMapping() {
        print("解析完成")
    }
    
}

// 用户类型
enum UserType: String, HandyJSONEnum {
    case NOMAL
    case VIP
}

// 居住地
class Address : HandyJSON {
    var province:String?
    var city:String?
    var county:String?
    var detailAddress:String?
    
    required init() {}
    
}
