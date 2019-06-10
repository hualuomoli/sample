//
//  SVProgressHUDViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/7.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import SVProgressHUD

class SVProgressHUDViewController: UIViewController {

    @IBOutlet weak var infoButton: UIButton!
    @IBOutlet weak var successButton: UIButton!
    @IBOutlet weak var failButton: UIButton!
    @IBOutlet weak var progressButton: UIButton!
    @IBOutlet weak var waitingButton: UIButton!
    @IBOutlet weak var closeButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        infoButton.addTarget(self, action: #selector(info), for: .touchUpInside)
        successButton.addTarget(self, action: #selector(success), for: .touchUpInside)
        failButton.addTarget(self, action: #selector(fail), for: .touchUpInside)
        progressButton.addTarget(self, action: #selector(progress), for: .touchUpInside)
        waitingButton.addTarget(self, action: #selector(waiting), for: .touchUpInside)
        closeButton.addTarget(self, action: #selector(close), for: .touchUpInside)
        
    }
    
    @objc func info() {
        SVProgressHUD.showInfo(withStatus: "信息")
    }
    
    @objc func success() {
        SVProgressHUD.showSuccess(withStatus: "成功")
    }
    
    @objc func fail() {
        SVProgressHUD.showError(withStatus: "错误")
    }
    
    @objc func progress() {
        SVProgressHUD.showProgress(0.8)
    }
    
    @objc func waiting() {
        SVProgressHUD.show(withStatus: "处理中")
        
        // 等待一段时间后关闭
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 1, execute: {
            SVProgressHUD.dismiss()
        })
        
    }
    
    @objc func close() {
        SVProgressHUD.dismiss()
    }



}
