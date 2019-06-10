//
//  BlockViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/8.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class BlockViewController: UIViewController {

    @IBOutlet weak var blockButton: UIButton!
    @IBOutlet weak var blockNeedResultButton: UIButton!
    @IBOutlet weak var blockWithParameterButton: UIButton!
    @IBOutlet weak var blockAllButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        blockButton.addTarget(self, action: #selector(blockClick), for: .touchUpInside)
        blockNeedResultButton.addTarget(self, action: #selector(blockNeedResultClick), for: .touchUpInside)
        blockWithParameterButton.addTarget(self, action: #selector(blockWithParameterClick), for: .touchUpInside)
        blockAllButton.addTarget(self, action: #selector(blockAllClick), for: .touchUpInside)
    }

    @objc func blockClick() {
        self.block {
            print("====== blockClick")
        }
    }
    
    @objc func blockNeedResultClick() {
        self.blockWithResult() {
            print("====== blockNeedResultClick")
            return "返回值"
        }
    }
    
    @objc func blockWithParameterClick() {
        self.blockWithParameter() {parameter in
            print("====== blockWithParameterClick with parameter \(parameter)")
        }
    }
    
    @objc func blockAllClick() {
        self.blockAll() {parameter in
            print("====== blockAllClick with parameter \(parameter)")
            return "result"
        }
    }
    
    func block(_ callback: () -> ()) {
        print("block method called.")
        callback()
        print("block method end.")
    }
    
    func blockWithResult(_ callback:() -> String) {
        print("blockWithResult method called.")
        let result = callback()
        print("callback result is \(result)")
        print("blockWithResult method end.")
    }
    
    func blockWithParameter(_ callback:(_ result:String) -> ()) {
        print("blockWithParameter method called.")
        callback("data")
        print("blockWithParameter method end.")
    }
    

    func blockAll(_ callback:(_ result:String) -> String) {
        print("blockAll method called.")
        let result = callback("data")
        print("callback result is \(result)")
        print("blockAll method end.")
    }

}
