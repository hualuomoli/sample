//
//  HandyJSONViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/13.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class HandyJSONViewController: UIViewController {
    
    
    @IBOutlet weak var serializeButton: UIButton!
    
    @IBOutlet weak var deserializeButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        
        deserializeButton.addTarget(self, action: #selector(deserializeClick), for: .touchUpInside)
    }
    
    
    @objc func serilize() {
        
    }
    
    @objc func deserializeClick() {
        let result = executeDeserialize()
        print("result:\(result)")
        
    }
    
    func executeDeserialize() -> String {
        let json = """
                    {
                        "id": "1234",
                        "username":"hualuomoli",
                        "nickname":"花落寞离",
                        "age": 18,
                        "hobbys": ["play", "work"],
                        "userType": "VIP",
                        "address": {
                                "province": "山东省",
                                "city": "青岛市",
                                "county": "市北区",
                                "detailAddress": "合肥路666号"
                        },
                        "registerDate": "2019-06-13"
                    }
                    """
        let user = User.deserialize(from: json)! as User
        
        guard user.userId!.elementsEqual("1234") else {return "id"}
        guard user.username!.elementsEqual("hualuomoli") else {return "musername "}
        guard user.nickname!.elementsEqual("花落寞离") else {return "nickname"}
        guard user.age! == 18 else {return "age"}
        guard user.hobbys!.count == 2 else {return "hobbys"}
        guard user.hobbys![0].elementsEqual("play") else {return "hobbys[0]"}
        guard user.hobbys![1].elementsEqual("work") else {return "hobbys[1]"}
        guard user.userType! == UserType.VIP else {return "userType"}
        guard user.address!.province!.elementsEqual("山东省") else {return "address.province"}
        guard user.address!.city!.elementsEqual("青岛市") else {return "address.city"}
        guard user.address!.county!.elementsEqual("市北区") else {return "address.county"}
        guard user.address!.detailAddress!.elementsEqual("合肥路666号") else {return "address.detailAddress"}
        let format = DateFormatter.init()
        format.dateFormat = "yyyy-MM-dd"
        let registerDateString = format.string(from: user.registerDate!)
        guard registerDateString.elementsEqual("2019-06-13") else {return "registerDate"}
        
        print("user info:\(user.toJSONString(prettyPrint: false)!)")
        
        return "success"
    }
    
    
}
