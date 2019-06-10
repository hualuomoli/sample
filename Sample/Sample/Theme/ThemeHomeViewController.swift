//
//  ThemeHomeViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/8.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class ThemeHomeViewController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        self.addChildController(ThemeViewController(), title:"默认")
        self.addChildController(ThemeUsePlistViewController(), title:"Plist")
        self.addChildController(ThemeConstantsViewController(), title:"常量")
        self.addChildController(ThemeUsePlistConstantsViewController(), title:"常量Plist")
    }
    
    func addChildController(_ controller:UIViewController, title:String) {
        controller.title = title
        super.addChild(controller)
    }




}
