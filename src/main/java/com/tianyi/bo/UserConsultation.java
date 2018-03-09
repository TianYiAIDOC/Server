package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by anhui on 2018/3/6.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class UserConsultation  extends BaseBo implements Serializable {


}
