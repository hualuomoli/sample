//
//  HUDViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/8.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class HUDViewController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        addChildController(SVProgressHUDViewController(), title: "SV")
    }


    func addChildController(_ controller:UIViewController, title:String) {
        controller.title = title
        super.addChild(controller)
    }

}
