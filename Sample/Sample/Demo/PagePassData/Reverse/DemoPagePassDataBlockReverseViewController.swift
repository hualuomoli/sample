//
//  DemoPagePassDataBlockReverseViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/17.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class DemoPagePassDataBlockReverseViewController: UIViewController {

    
    @IBOutlet weak var dataText: UITextField!
    @IBOutlet weak var reverseBlockButton: UIButton!
    
    // 定义block
    typealias callbackBlock = (_ data:String) -> Void
    
    //创建block变量
    var callback:callbackBlock!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        reverseBlockButton.addTarget(self, action: #selector(reverseBlockClick), for: .touchUpInside)
    }
    
    @objc func reverseBlockClick() {
        // 调用block
        self.callback(dataText.text!)
        self.dismiss(animated: true, completion: nil)
    }


    
    

}
