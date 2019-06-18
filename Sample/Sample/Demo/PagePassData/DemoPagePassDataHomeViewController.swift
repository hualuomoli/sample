//
//  DemoPagePassDataHomeViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/17.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class DemoPagePassDataHomeViewController: UIViewController, DemoPagePassDataReverseDelegate  {

    @IBOutlet weak var dataText: UITextField!
    @IBOutlet weak var attributeButton: UIButton!
    @IBOutlet weak var singleButton: UIButton!
    @IBOutlet weak var userDefaultsButton: UIButton!
    @IBOutlet weak var reverseButton: UIButton!
    @IBOutlet weak var blockButton: UIButton!
    @IBOutlet weak var notificationReverseButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        attributeButton.addTarget(self, action: #selector(gotoAttribute), for: .touchUpInside)
        singleButton.addTarget(self, action: #selector(gotoSingle), for: .touchUpInside)
        userDefaultsButton.addTarget(self, action: #selector(gotoUserDefaults), for: .touchUpInside)
        reverseButton.addTarget(self, action: #selector(reverseClick), for: .touchUpInside)
        blockButton.addTarget(self, action: #selector(blockReverseClick), for: .touchUpInside)
        notificationReverseButton.addTarget(self, action: #selector(notificationReverseClick), for: .touchUpInside)
    }

    // 属性传值
    @objc func gotoAttribute() {
        let controller = DemoPagePassDataAttributeViewController()
        controller.data = dataText.text
       self.present(controller, animated: true, completion: nil)
    }

    // 单例传值
    @objc func gotoSingle() {
        let controller = DemoPagePassDataSingleInstantsViewController()
        DemoPagePassDataSingleInstants.single.data = dataText.text
        self.present(controller, animated: true, completion: nil)
    }
   
    // UserDefaults
    @objc func gotoUserDefaults() {
        let controller = DemoPagePassDataUserDefaultsViewController()
        UserDefaults.standard.set(dataText.text, forKey: PAGE_PASS_DATA_USER_DEFAULTS_DATA)
        self.present(controller, animated: true, completion: nil)
    }

    // delegate 回传
    @objc func reverseClick() {
        let controller = DemoPagePassDataDelegateReverseViewController()
        controller.deletate = self
        self.present(controller, animated: true, completion: nil)
    }
    
    // delegate 回传
    func reverseSuccess(_ data: String) {
        dataText.text = data
    }
    
    // block回传
    @objc func blockReverseClick() {
        let controller = DemoPagePassDataBlockReverseViewController()
        controller.callback = {data in
            self.dataText.text = data
        }
        self.present(controller, animated: true, completion: nil)
    }
    
    // 通知
    @objc func notificationReverseClick() {
        let controller = DemoPagePassDataNotificationReverseViewController()
        let name = NSNotification.Name(rawValue: PAGE_PASS_DATA_NOTIFICATION_CENTER_CALLBACK)
        NotificationCenter.default.addObserver(self, selector: #selector(notificationCallback(_:)), name: name, object: nil)
        self.present(controller, animated: true, completion: nil)
    }
    
    @objc func notificationCallback(_ notification:Notification) {
        self.dataText.text = notification.userInfo?[PAGE_PASS_DATA_NOTIFICATION_CENTER_DATA] as? String
    }
    
}
