//
//  DemoPagePassDataSingleInstantsViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/17.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class DemoPagePassDataSingleInstantsViewController: UIViewController {

    @IBOutlet weak var rebackSingleInstantsButton: UIButton!
    @IBOutlet weak var dataSingleInstantsLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        dataSingleInstantsLabel.text = DemoPagePassDataSingleInstants.single.data
        rebackSingleInstantsButton.addTarget(self, action: #selector(rebackClick), for: .touchUpInside)
    }

    @objc func rebackClick() {
        self.dismiss(animated: true, completion: nil)
    }

  
}
