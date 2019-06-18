//
//  DemoPagePassDataUserDefaultsViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/17.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class DemoPagePassDataUserDefaultsViewController: UIViewController {

    @IBOutlet weak var dataUserDefaultsLabel: UILabel!
    @IBOutlet weak var rebackUserDefaultsButton: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dataUserDefaultsLabel.text = UserDefaults.standard.string(forKey: PAGE_PASS_DATA_USER_DEFAULTS_DATA)
        rebackUserDefaultsButton.addTarget(self, action: #selector(rebackClick), for: .touchUpInside)
    }


    @objc func rebackClick() {
        self.dismiss(animated: true, completion: nil)
    }

}
