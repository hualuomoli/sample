//
//  ThemeConstantsViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/8.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import SwiftTheme

// 使用常量
class ThemeConstantsViewController: UIViewController {

    @IBOutlet weak var updateThemeButton: UIButton!
    
    var index:Int = 0
    let theme_bgc:ThemeColorPicker = ["#90caf9", "#ffeb3b"]
    let theme_name_button_title:ThemeColorPicker = ["#b39ddb","#ffa726"]
    let theme_name_button_bgc:ThemeColorPicker = ["#84ffff","#ba68c8"]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.theme_backgroundColor = theme_bgc
        self.updateThemeButton.theme_tintColor = theme_name_button_title
        self.updateThemeButton.theme_backgroundColor = theme_name_button_bgc
        
        self.updateThemeButton.addTarget(self, action: #selector(updateTheme), for: .touchUpInside)
        
        
    }
    
    @objc func updateTheme() {
        self.index = self.index + 1
        ThemeManager.setTheme(index: self.index % 2)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        index = 0
        ThemeManager.setTheme(index: index)
    }
    
}
