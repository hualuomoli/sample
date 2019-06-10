//
//  Result.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/6.
//  Copyright © 2019 刘保全. All rights reserved.
//

import Foundation

class Result<T> {
    
    var success:Bool!
    var data:T!
    var message:String!
    
    init(_ success:Bool, data:T) {
        self.success = success
        self.data = data
        self.message = nil
    }
    
    init(_ success:Bool, message:String) {
        self.success = success
        self.data = nil
        self.message = message
    }
    
    init(_ success:Bool, data:T, message:String) {
        self.success = success
        self.data = data
        self.message = message
    }
    
    
}
