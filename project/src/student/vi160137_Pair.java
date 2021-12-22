/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import rs.etf.sab.operations.PackageOperations.Pair;

/**
 *
 * @author vi160137d
 */
public class vi160137_Pair<A,B> implements Pair {
    private A prvi;
    private B drugi;
    
    public vi160137_Pair(A a,B b) {
        prvi = a;
        drugi = b;
    }

    @Override
    public Object getFirstParam() {
        return prvi;
    }

    @Override
    public Object getSecondParam() {
        return drugi;
    }
    
}
