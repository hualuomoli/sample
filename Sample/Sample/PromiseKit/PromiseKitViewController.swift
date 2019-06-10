//
//  PromiseKitViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/7.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import PromiseKit


class PromiseKitViewController: UIViewController {
    
    
    
    @IBOutlet weak var asyncButton: UIButton!
    @IBOutlet weak var allButton: UIButton!
    @IBOutlet weak var anyButton: UIButton!
    @IBOutlet weak var errorButton: UIButton!
    @IBOutlet weak var sampleButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        asyncButton.addTarget(self, action: #selector(async), for: .touchUpInside)
        allButton.addTarget(self, action: #selector(all), for: .touchUpInside)
        anyButton.addTarget(self, action: #selector(any), for: .touchUpInside)
        errorButton.addTarget(self, action: #selector(error), for: .touchUpInside)
        sampleButton.addTarget(self, action: #selector(sample), for: .touchUpInside)
    }
    
    @objc func async() {
        print("async")
        
        deal("异步处理完成", sleep: 1)
            .done{ result in
                print("\(result)")
            }
            .catch { (error:Error) in
                print(error)
        }
        
        //        deal("异步处理完成", sleep: 1)
        //            .done{
        //                print("\($0)")
        //        }
        
        
    }
    
    @objc func all() {
        print("all")
        
        when(fulfilled:deal("方法1", sleep: 3), deal("方法2", sleep:1))
            .done({result1, result2 in
                print("result1:\(result1), result2:\(result2)")
            })
            .catch { (error:Error) in
                print(error)
        }
        
    }
    
    @objc func any() {
        print("any")
        
        race(deal("first", sleep: 3), deal("second", sleep: 1))
            .done({ result in
                print(result)
            })
            .catch { (error:Error) in
                print(error)
        }
        
        
    }
    
    @objc func error() {
        print("error")
        
        func deal() -> Promise<String> {
            return Promise<String>{resolver in
                resolver.reject(PromiseKitError.DEFAULT)
            }
        }
        
        deal()
            .catch({err in
                print(err)
            })
    }
    
    @objc func sample() {
        print("sample")
        
        
        deal("first", sleep: 1)
            .then({ (result:String) -> Promise<String> in
                print("第一步执行完成，返回结果\(result)")
                return self.deal("second", sleep: 2)
            })
            .then({ (result:String) -> Promise<String> in
                print("第二步执行完成，返回结果\(result)")
                return self.deal("third", sleep: 3)
            })
            .map({ (result:String) -> String in
                print("第二步执行完成，返回结果\(result)")
                return "转换后的数据：\(result)"
            })
            .ensure({
                print("数据已加载完成，操作UI控价 ")
            })
            .done({(result:String) in
                print("end result is \(result)")
            })
            .catch { (error:Error) in
                print("业务执行失败")
        }
        
    }
    
    // 异步处理
    func deal(_ result:String, sleep:Int) -> Promise<String> {
        
        return Promise<String>{ resolver in
            
            // 模拟等待
            after(.seconds(sleep)).done({
                resolver.fulfill(result)
            })
            
        }
        
    }
    
    
}
