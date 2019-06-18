//
//  DemoPagePassDataDelegateReverseViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/17.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class DemoPagePassDataDelegateReverseViewController: UIViewController {

    @IBOutlet weak var reverseText: UITextField!
    @IBOutlet weak var confirmButton: UIButton!
    
    var deletate:DemoPagePassDataReverseDelegate?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        confirmButton.addTarget(self, action: #selector(confirmClick), for: .touchUpInside)
    }

    @objc func confirmClick() {
        deletate?.reverseSuccess(reverseText.text!)
        self.dismiss(animated: true, completion: nil)
    }


}


protocol DemoPagePassDataReverseDelegate {
    
    func reverseSuccess(_ data:String)
    
}
