//
//  AddressBookViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/6.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class AddressBookViewController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()

        self.view.backgroundColor = UIColor.gray
        self.pushViewController(AddressBookLoginViewController(), animated: false)
    }



}
