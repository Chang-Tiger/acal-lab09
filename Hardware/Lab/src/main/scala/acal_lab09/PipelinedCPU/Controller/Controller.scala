package acal_lab09.PiplinedCPU.Controller

import chisel3._
import chisel3.util._

import acal_lab09.PiplinedCPU.opcode_map._
import acal_lab09.PiplinedCPU.condition._
import acal_lab09.PiplinedCPU.inst_type._
import acal_lab09.PiplinedCPU.alu_op_map._
import acal_lab09.PiplinedCPU.pc_sel_map._
import acal_lab09.PiplinedCPU.wb_sel_map._
import acal_lab09.PiplinedCPU.forwarding_sel_map._

class Controller(memAddrWidth: Int) extends Module {
  val io = IO(new Bundle {
    // Memory control signal interface
    val IM_Mem_R = Output(Bool())
    val IM_Mem_W = Output(Bool())
    val IM_Length = Output(UInt(4.W))
    val IM_Valid = Input(Bool())

    val LD_ST = Output(UInt(2.W))

    val DM_Mem_R = Output(Bool())
    val DM_Mem_W = Output(Bool())
    val DM_Length = Output(UInt(4.W))
    val DM_Valid = Input(Bool())

    // branch Comp.
    val E_BrEq = Input(Bool())
    val E_BrLT = Input(Bool())

    // Branch Prediction
    val E_Branch_taken = Output(Bool())
    val E_En = Output(Bool())

    val ID_pc = Input(UInt(memAddrWidth.W))
    val EXE_target_pc = Input(UInt(memAddrWidth.W))

    // Flush
    //val Flush_WB_ID_DH = Output(Bool()) //TBD
    val Flush_BH = Output(Bool()) //TBD
    val Flush_MEM_ID_DH = Output(Bool())
    val Flush_EXE_ID_DH = Output(Bool())
    val Flush_WB_ID_DH = Output(Bool())

    // Stall
    // To Be Modified
    //val Stall_DH = Output(Bool()) //TBD
    val Stall_MA = Output(Bool()) //TBD
    val Stall_EXE_ID_DH = Output(Bool())
    val Stall_MEM_ID_DH= Output(Bool())
    val Stall_WB_ID_DH= Output(Bool())

    // inst
    val IF_Inst = Input(UInt(32.W))
    val ID_Inst = Input(UInt(32.W))
    val EXE_Inst = Input(UInt(32.W))
    val MEM_Inst = Input(UInt(32.W))
    val WB_Inst = Input(UInt(32.W))

    // sel
    val PCSel = Output(UInt(2.W))
    val D_ImmSel = Output(UInt(3.W))
    val W_RegWEn = Output(Bool())
    val E_BrUn = Output(Bool())
    val E_ASel = Output(UInt(2.W))
    val E_BSel = Output(UInt(1.W))
    val E_ALUSel = Output(UInt(15.W))
    val W_WBSel = Output(UInt(2.W))

    val Hcf = Output(Bool())
  })
  // Inst Decode for each stage
  val IF_opcode = io.IF_Inst(6, 0)

  val ID_opcode = io.ID_Inst(6, 0)
  val ID_rs1 = io.ID_Inst(19, 15)//for stall
  val ID_rs2 = io.ID_Inst(24, 20)//for stall

  val EXE_opcode = io.EXE_Inst(6, 0)
  val EXE_funct3 = io.EXE_Inst(14, 12)
  val EXE_funct7 = io.EXE_Inst(31, 25)
  val EXE_rd = io.EXE_Inst(11, 7)

  val MEM_opcode = io.MEM_Inst(6, 0)
  val MEM_funct3 = io.MEM_Inst(14, 12)
  val MEM_rd = io.MEM_Inst(11, 7)

  val WB_opcode = io.WB_Inst(6, 0)
  val WB_rd = io.WB_Inst(11, 7)

  // Control signal - Branch/Jump
  val E_En = Wire(Bool())
  E_En := (EXE_opcode===BRANCH || EXE_opcode === JAL || EXE_opcode === JALR)         // To Be Modified//
  val E_Branch_taken = Wire(Bool())
  E_Branch_taken := MuxLookup(EXE_opcode, false.B, Seq(
          //BRANCH -> true.B,
          BRANCH -> MuxLookup(EXE_funct3, false.B, Seq(
            "b000".U(3.W) -> io.E_BrEq,//beq
            "b001".U(3.W) -> (~io.E_BrEq),//bne
            "b100".U(3.W) -> io.E_BrLT,//blt
            "b101".U(3.W) -> (~io.E_BrLT),//bge
            "b110".U(3.W) -> io.E_BrLT,//bltu
            "b111".U(3.W) -> (~io.E_BrLT),//bgeu
          )),
          JAL -> true.B,
          JALR -> true.B,
        ))    // To Be Modified

  io.E_En := E_En
  io.E_Branch_taken := E_Branch_taken

  // pc predict miss signal
  val Predict_Miss = Wire(Bool())
  Predict_Miss := (E_En && E_Branch_taken && io.ID_pc=/=io.EXE_target_pc)

  // Control signal - PC
  when(Predict_Miss){
    io.PCSel := EXE_T_PC
  }.otherwise{
    io.PCSel := IF_PC_PLUS_4
  }

  // Control signal - Branch comparator
  io.E_BrUn := (io.EXE_Inst(13) === 1.U)

  // Control signal - Immediate generator
  io.D_ImmSel := MuxLookup(ID_opcode, 0.U, Seq(
    OP_IMM -> I_type,//"b0010011"
    LOAD -> I_type,//"b0000011"
    BRANCH -> B_type,//"b1100011"
    LUI -> U_type,//"b0110111"
    OP -> R_type,//"b0110011"
    STORE -> S_type,//"b0100011"
    JALR -> I_type,//"b1100111"
    JAL -> J_type,//"b1101111"
    AUIPC -> U_type,
  )) // To Be Modified  ok

  // Control signal - Scalar ALU
  io.E_ASel := MuxLookup(EXE_opcode, 0.U, Seq(
    BRANCH -> 1.U,
    LUI -> 2.U,

    OP -> 0.U,
    OP_IMM -> 0.U,
    LOAD -> 0.U,//
    STORE -> 0.U,//
    JALR -> 0.U,
    JAL -> 1.U,
    AUIPC -> 1.U,
  ))    // To Be Modified
  io.E_BSel  := MuxLookup(EXE_opcode, 0.U, Seq(
    BRANCH -> 1.U,
    LUI -> 1.U,

    OP -> 0.U,
    OP_IMM -> 1.U,
    LOAD -> 1.U,
    STORE -> 1.U,
    JALR -> 1.U,
    JAL -> 1.U,
    AUIPC -> 1.U,
  ))  // To Be Modified

  io.E_ALUSel := MuxLookup(EXE_opcode, (Cat(0.U(7.W), "b11111".U, 0.U(3.W))), Seq(
    OP -> (Cat(EXE_funct7, "b11111".U, EXE_funct3)),
    OP_IMM -> MuxLookup(EXE_funct3, (Cat(0.U(7.W), "b11111".U, EXE_funct3)), Seq(
      "b101".U -> Mux(EXE_funct7 === "b0100000".U, (Cat(EXE_funct7, "b11111".U, EXE_funct3)), (Cat(0.U(7.W), "b11111".U, EXE_funct3))),
    )),

    
  )) // To Be Modified

  // Control signal - Data Memory
  io.DM_Mem_R := (MEM_opcode===LOAD)
  io.DM_Mem_W := (MEM_opcode===STORE)
  io.DM_Length := Cat(0.U(1.W),MEM_funct3) // length

  // Control signal - Inst Memory
  io.IM_Mem_R := true.B // always true
  io.IM_Mem_W := false.B // always false
  io.IM_Length := "b0010".U // always load a word(inst)

  // Control signal - Scalar Write Back
  io.W_RegWEn := MuxLookup(WB_opcode, false.B, Seq(
    OP_IMM -> true.B,
    LOAD -> true.B,
    LUI -> true.B,

    OP -> true.B,
    JALR -> true.B,//
    JAL -> true.B,//
    AUIPC -> true.B,
  ))  // To Be Modified


  io.W_WBSel := MuxLookup(WB_opcode, ALUOUT, Seq(
    LOAD -> LD_DATA,
    JALR -> PC_PLUS_4,//
    JAL -> PC_PLUS_4,//
    //BRANCH
  )) // To Be Modified 

  // Control signal - Others
  io.Hcf := (IF_opcode === HCF)

  /****************** Data Hazard(DH) ******************/
  // val ID_use_rs1, ID_use_rs2, EXE_use_rd, MEM_use_rd, WB_use_rd, ID_EXE_DH, ID_MEM_DH, ID_WB_DH = Wire(Bool())
  // ID_use_rs1 := ((ID_opcode =/= JAL && ID_opcode =/= LUI && ID_opcode =/= AUIPC) && ID_rs1 =/= 0.U(5.W))
  // ID_use_rs2 := ((ID_opcode === OP || ID_opcode === STORE || ID_opcode === BRANCH) && ID_rs2 =/= 0.U(5.W))//0?
  
  // EXE_use_rd := ((EXE_opcode =/= STORE && EXE_opcode =/= BRANCH) && EXE_rd =/= 0.U)
  // MEM_use_rd := ((MEM_opcode =/= STORE && MEM_opcode =/= BRANCH) && MEM_rd =/= 0.U)
  // WB_use_rd := ((WB_opcode =/= STORE && WB_opcode =/= BRANCH) && WB_rd =/= 0.U)
  
  // ID_EXE_DH := (ID_use_rs1 && EXE_use_rd && ID_rs1 === EXE_rd)  || (ID_use_rs2 && EXE_use_rd && ID_rs2 === EXE_rd)
  // ID_MEM_DH := (ID_use_rs1 && MEM_use_rd && ID_rs1 === MEM_rd)  || (ID_use_rs2 && MEM_use_rd && ID_rs2 === MEM_rd)
  // ID_WB_DH := (ID_use_rs1 && WB_use_rd && ID_rs1 === WB_rd)  || (ID_use_rs2 && WB_use_rd && ID_rs2 === WB_rd)


  val is_ID_use_rs1 = Wire(Bool()) 
  val is_ID_use_rs2 = Wire(Bool())
  is_ID_use_rs1 := MuxLookup(ID_opcode,false.B,Seq(
    BRANCH -> true.B,
    OP ->  true.B,
    OP_IMM -> true.B,
    STORE -> true.B,
    LOAD -> true.B,
    JALR -> true.B,
  ))   // To Be Modified
  is_ID_use_rs2 := MuxLookup(ID_opcode,false.B,Seq(
    BRANCH -> true.B,
    OP ->  true.B,
    STORE -> true.B,
  ))   // To Be Modified

  // Use rd in WB stage
  val is_WB_use_rd = Wire(Bool())
  val is_EXE_use_rd = Wire(Bool())
  val is_MEM_use_rd = Wire(Bool())
  is_WB_use_rd := MuxLookup(WB_opcode,false.B,Seq(
    JAL -> true.B,
    OP ->  true.B,
    OP_IMM -> true.B,
    AUIPC -> true.B,
    LUI -> true.B,
    LOAD -> true.B,
    JALR -> true.B,
  ))   // To Be Modified
  is_EXE_use_rd := MuxLookup(EXE_opcode,false.B,Seq(
    JAL -> true.B,
    OP ->  true.B,
    OP_IMM -> true.B,
    AUIPC -> true.B,
    LUI -> true.B,
    LOAD -> true.B,
    JALR -> true.B,
  ))   // To Be Modified
  is_MEM_use_rd := MuxLookup(MEM_opcode,false.B,Seq(
    JAL -> true.B,
    OP ->  true.B,
    OP_IMM -> true.B,
    AUIPC -> true.B,
    LUI -> true.B,
    LOAD -> true.B,
    JALR -> true.B,
  ))   // To Be Modified

  // Hazard condition (rd, rs overlap)
  val is_ID_rs1_WB_rd_overlap = Wire(Bool())
  val is_ID_rs2_WB_rd_overlap = Wire(Bool())
  val is_ID_rs1_EXE_rd_overlap = Wire(Bool())
  val is_ID_rs2_EXE_rd_overlap = Wire(Bool())
  val is_ID_rs1_MEM_rd_overlap = Wire(Bool())
  val is_ID_rs2_MEM_rd_overlap = Wire(Bool())

  is_ID_rs1_WB_rd_overlap := is_ID_use_rs1 && is_WB_use_rd && (ID_rs1 === WB_rd) && (WB_rd =/= 0.U(5.W))
  is_ID_rs2_WB_rd_overlap := is_ID_use_rs2 && is_WB_use_rd && (ID_rs2 === WB_rd) && (WB_rd =/= 0.U(5.W))
  is_ID_rs1_EXE_rd_overlap := is_ID_use_rs1 && is_EXE_use_rd && (ID_rs1 === EXE_rd) && (EXE_rd =/= 0.U(5.W))
  is_ID_rs2_EXE_rd_overlap := is_ID_use_rs2 && is_EXE_use_rd && (ID_rs2 === EXE_rd) && (EXE_rd =/= 0.U(5.W))
  is_ID_rs1_MEM_rd_overlap := is_ID_use_rs1 && is_MEM_use_rd && (ID_rs1 === MEM_rd) && (MEM_rd =/= 0.U(5.W))
  is_ID_rs2_MEM_rd_overlap := is_ID_use_rs2 && is_MEM_use_rd && (ID_rs2 === MEM_rd) && (MEM_rd =/= 0.U(5.W))

  // Control signal - Stall
  // Stall for Data Hazard
  io.Stall_EXE_ID_DH := (is_ID_rs1_EXE_rd_overlap || is_ID_rs2_EXE_rd_overlap)
  io.Stall_MEM_ID_DH := (is_ID_rs1_MEM_rd_overlap || is_ID_rs2_MEM_rd_overlap)
  io.Stall_WB_ID_DH := (is_ID_rs1_WB_rd_overlap || is_ID_rs2_WB_rd_overlap)

  // Control signal - Flush
  io.Flush_EXE_ID_DH := (is_ID_rs1_EXE_rd_overlap || is_ID_rs2_EXE_rd_overlap)
  io.Flush_MEM_ID_DH := (is_ID_rs1_MEM_rd_overlap || is_ID_rs2_MEM_rd_overlap)
  io.Flush_WB_ID_DH := (is_ID_rs1_WB_rd_overlap || is_ID_rs2_WB_rd_overlap)
  // Control signal - Data Forwarding (Bonus)
  /****************** Data Hazard End******************/


  // Control signal - Stall
  //io.Stall_DH := false.B // Stall for Data Hazard   TODO
  //io.Stall_IF := (ID_EXE_DH || ID_MEM_DH || ID_WB_DH) //&& ~E_Branch_taken
  
  io.Stall_MA := false.B//(MEM_opcode === LOAD || MEM_opcode === STORE)// Stall for Waiting Memory Access
  io.LD_ST :=  MuxLookup(MEM_opcode, 0.U, Seq(
    LOAD -> 1.U,
    STORE -> 2.U,
  ))
  // Control signal - Flush
  // io.Flush_EXE := E_Branch_taken || (ID_EXE_DH || ID_MEM_DH || ID_WB_DH)
  io.Flush_BH := Predict_Miss

  // Control signal - Data Forwarding (Bonus)

  /****************** Data Hazard End******************/


}
