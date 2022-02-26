;mydll.asm
section .text

global Java_com_example_systemprogramm_controllermodels_lowlevelfunction_LowLevelFunction_div
    
Java_Java_com_example_systemprogramm_controllermodels_lowlevelfunction_LowLevelFunction_div:
    mov eax, [esp + 12] ; игнорируем первые 2 параметра
    add eax, [esp + 16]
    ret 16
    
end