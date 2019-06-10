//
//  ThemeUsePlistConstantsViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/8.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import SwiftTheme

// 使用常量
class ThemeUsePlistConstantsViewController: UIViewController {

    @IBOutlet weak var updateThemeButton: UIButton!
    
    var themes = ["theme_default","theme_dark", "theme_light"]
    var index:Int = 0
    
    let theme_name_bgc:ThemeColorPicker = "background_color"
    let theme_name_button_title:ThemeColorPicker = "button.title"
    let theme_name_button_bgc:ThemeColorPicker = "button.background"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        ThemeManager.setTheme(plistName: themes[0], path: .mainBundle)
        
        self.view.theme_backgroundColor = theme_name_bgc
        self.updateThemeButton.theme_tintColor = theme_name_button_title
        self.updateThemeButton.theme_backgroundColor = theme_name_button_bgc
        
        updateThemeButton.addTarget(self, action: #selector(updateTheme), for: .touchUpInside)
    }
    
    @objc func updateTheme() {
        index = (index + 1) % themes.count
        ThemeManager.setTheme(plistName: themes[index], path: .mainBundle)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        ThemeManager.setTheme(plistName: themes[0], path: .mainBundle)
    }

}
