//
//  AddressBookInfosViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/6.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class AddressBookInfosViewController: UIViewController {
    
    var username:String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.backgroundColor = UIColor.gray
        print("用户名:\(username)")
        
        
    }
    
    
    func configUsername(username: String) {
        self.username = username
    }
    
    
    
}
