//
//  AddressBookLoginViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/6.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import SVProgressHUD
import PromiseKit


class AddressBookLoginViewController: UIViewController {
    
    @IBOutlet weak var usernameText: UITextField!
    @IBOutlet weak var passwordText: UITextField!
    @IBOutlet weak var rememberSwitch: UISwitch!
    @IBOutlet weak var autoLoginSwitch: UISwitch!
    @IBOutlet weak var loginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        usernameText.addTarget(self, action: #selector(validate), for: .editingChanged)
        passwordText.addTarget(self, action: #selector(validate), for: .editingChanged)
        
        loginButton.addTarget(self, action: #selector(login), for: .touchUpInside)
        
    }
    
    
    
    @objc func validate(){
        
        loginButton.isEnabled = false
        
        
        if(!usernameText.hasText) {
            return
        }
        
        if(!passwordText.hasText) {
            return
        }
        
        loginButton.isEnabled = true
        
    }
    
    @objc func login() {
        
        SVProgressHUD.show(withStatus: "登录中")
        
        doLogin()
            .done({ result in
                SVProgressHUD.dismiss()
                
                if(result.success) {
                    //
                    print("登录成功,正在跳转")
                    self.save()
                    
                    let controller = AddressBookInfosViewController()
                    controller.configUsername(username: self.usernameText.text!)
                    
                    self.navigationController?.pushViewController(controller, animated: true)
                    
                } else {
                    SVProgressHUD.showError(withStatus: "用户名或密码错误")
                }
            })
            .catch { (error:Error) in
                print(error)
        }
        
        
    }
    
    // save
    func save() {
        
        if(!rememberSwitch.isOn) {
            return
        }
        
    }
    
    func doLogin() -> Promise<Result<String>> {
        return Promise<Result<String>> {resolver in
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2, execute: {
                let username = self.usernameText.text!
                let password = self.passwordText.text!
                if(username.elementsEqual("admin") && password.elementsEqual("123456")) {
                    resolver.fulfill(Result.init(true, data: "1234", message: "登录成功"))
                } else {
                    resolver.fulfill(Result.init(false, message: "用户名或密码错误"))
                }
                // end
            })
        }
    }
    
    
}
