//
//  DemoPagePassDataNotificationReverseViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/17.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class DemoPagePassDataNotificationReverseViewController: UIViewController {

    @IBOutlet weak var sendNotificationButton: UIButton!
    @IBOutlet weak var notificationText: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        sendNotificationButton.addTarget(self, action: #selector(notificationClick), for: .touchUpInside)
        
    }
    
    @objc func notificationClick() {
        let name = NSNotification.Name(rawValue: PAGE_PASS_DATA_NOTIFICATION_CENTER_CALLBACK)
        let userInfo = [PAGE_PASS_DATA_NOTIFICATION_CENTER_DATA: notificationText.text!]
        NotificationCenter.default.post(name: name, object: nil, userInfo: userInfo)
        self.dismiss(animated: true, completion: nil)
    }

}
