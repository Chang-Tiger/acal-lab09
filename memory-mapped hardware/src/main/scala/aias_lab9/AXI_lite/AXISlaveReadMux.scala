package aias_lab9.AXILite

import chisel3._
import chisel3.util._
import aias_lab9.AXILite.AXILITE_PARAMS._

class readOut extends Bundle{
    val readAddr = Decoupled(new AXILiteAddress(ADDR_WIDTH))                   // output address(from read bus) to slave
    val readData = Flipped(Decoupled(new AXILiteReadData(DATA_WIDTH)))           // input read data from slave
}
class readIn extends Bundle{
    val readAddr = Flipped(Decoupled(new AXILiteAddress(ADDR_WIDTH))) // input address from readbus
    val readData = Decoupled(new AXILiteReadData(DATA_WIDTH))             // output read data(from slave) to readbus
}

class AXISlaveReadMux(nMasters:Int)extends Module{
    val io = IO(new Bundle{
        val out = new readOut
        val ins = Vec(nMasters, new readIn)
    })
    val arbiter = Module(new RRArbiter(new AXILiteAddress(ADDR_WIDTH),nMasters))

    for(i <- 0 until nMasters){
        arbiter.io.in(i) <> io.ins(i).readAddr
    }
    //arbiter.io.in <> io.ins.readAddr
    io.out.readAddr <> arbiter.io.out
    for(i <- 0 until nMasters){
        io.ins(i).readData.bits.data := io.out.readData.bits.data
        io.ins(i).readData.valid := false.B
        io.ins(i).readData.bits.resp := 0.U
    }
    io.ins(arbiter.io.chosen.asUInt).readData <> io.out.readData

}

