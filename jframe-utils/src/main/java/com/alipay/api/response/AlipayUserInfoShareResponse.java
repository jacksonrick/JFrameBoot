package com.alipay.api.response;

import java.util.List;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;
import com.alipay.api.domain.AlipayUserDeliverAddress;
import com.alipay.api.domain.AlipayUserPicture;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.user.info.share response.
 * 
 * @author auto create
 * @since 1.0, 2017-04-14 20:17:09
 */
public class AlipayUserInfoShareResponse extends AlipayResponse {

	private static final long serialVersionUID = 6524586541884293963L;

	/** 
	 * 详细地址。
	 */
	@ApiField("address")
	private String address;

	/** 
	 * 区县名称。
	 */
	@ApiField("area")
	private String area;

	/** 
	 * 用户头像地址
	 */
	@ApiField("avatar")
	private String avatar;

	/** 
	 * 经营/业务范围（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("business_scope")
	private String businessScope;

	/** 
	 * 证件号码，结合证件类型使用.
	 */
	@ApiField("cert_no")
	private String certNo;

	/** 
	 * 0:身份证
1:护照
2:军官证
3:士兵证
4:回乡证
5:临时身份证
6:户口簿
7:警官证
8:台胞证
9:营业执照
10:其它证件
11:港澳居民来往内地通行证
12:台湾居民来往大陆通行证
	 */
	@ApiField("cert_type")
	private String certType;

	/** 
	 * 市名称。
	 */
	@ApiField("city")
	private String city;

	/** 
	 * 国家码
	 */
	@ApiField("country_code")
	private String countryCode;

	/** 
	 * 收货地址列表
	 */
	@ApiListField("deliver_addresses")
	@ApiField("alipay_user_deliver_address")
	private List<AlipayUserDeliverAddress> deliverAddresses;

	/** 
	 * 用户支付宝邮箱登录名
	 */
	@ApiField("email")
	private String email;

	/** 
	 * 企业代理人证件有效期（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_agent_person_cert_expiry_date")
	private String firmAgentPersonCertExpiryDate;

	/** 
	 * 企业代理人证件号码（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_agent_person_cert_no")
	private String firmAgentPersonCertNo;

	/** 
	 * 企业代理人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_agent_person_cert_type")
	private String firmAgentPersonCertType;

	/** 
	 * 企业代理人姓名（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_agent_person_name")
	private String firmAgentPersonName;

	/** 
	 * 企业法人证件有效期（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_legal_person_cert_expiry_date")
	private String firmLegalPersonCertExpiryDate;

	/** 
	 * 法人证件号码（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_legal_person_cert_no")
	private String firmLegalPersonCertNo;

	/** 
	 * 企业法人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_legal_person_cert_type")
	private String firmLegalPersonCertType;

	/** 
	 * 企业法人名称（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("firm_legal_person_name")
	private String firmLegalPersonName;

	/** 
	 * 企业法人证件图片（用户类型是公司类型时才有此字段）。
	 */
	@ApiListField("firm_legal_person_pictures")
	@ApiField("alipay_user_picture")
	private List<AlipayUserPicture> firmLegalPersonPictures;

	/** 
	 * 企业相关证件图片，包含图片地址（目前需要调用alipay.user.certify.image.fetch转换一下）及类型（用户类型是公司类型时才有此字段）。
	 */
	@ApiListField("firm_pictures")
	@ApiField("alipay_user_picture")
	private List<AlipayUserPicture> firmPictures;

	/** 
	 * 公司类型，包括（用户类型是公司类型时才有此字段）：
CO(公司)
INST(事业单位),
COMM(社会团体),
NGO(民办非企业组织),
STATEORGAN(党政国家机关)
	 */
	@ApiField("firm_type")
	private String firmType;

	/** 
	 * 性别（F：女性；M：男性）。
	 */
	@ApiField("gender")
	private String gender;

	/** 
	 * 余额账户是否被冻结。
T--被冻结；F--未冻结
	 */
	@ApiField("is_balance_frozen")
	private String isBalanceFrozen;

	/** 
	 * 是否通过实名认证。T是通过 F是没有实名认证。
	 */
	@ApiField("is_certified")
	private String isCertified;

	/** 
	 * 是否是学生
	 */
	@ApiField("is_student_certified")
	private String isStudentCertified;

	/** 
	 * 营业执照有效期，yyyyMMdd或长期，（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("license_expiry_date")
	private String licenseExpiryDate;

	/** 
	 * 企业执照号码（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("license_no")
	private String licenseNo;

	/** 
	 * 支付宝会员等级
	 */
	@ApiField("member_grade")
	private String memberGrade;

	/** 
	 * 手机号码。
	 */
	@ApiField("mobile")
	private String mobile;

	/** 
	 * 用户昵称
	 */
	@ApiField("nick_name")
	private String nickName;

	/** 
	 * 组织机构代码（用户类型是公司类型时才有此字段）。
	 */
	@ApiField("organization_code")
	private String organizationCode;

	/** 
	 * 个人用户生日。
	 */
	@ApiField("person_birthday")
	private String personBirthday;

	/** 
	 * 证件有效期（用户类型是个人的时候才有此字段）。
	 */
	@ApiField("person_cert_expiry_date")
	private String personCertExpiryDate;

	/** 
	 * 个人证件图片（用户类型是个人的时候才有此字段）。
	 */
	@ApiListField("person_pictures")
	@ApiField("alipay_user_picture")
	private List<AlipayUserPicture> personPictures;

	/** 
	 * 电话号码。
	 */
	@ApiField("phone")
	private String phone;

	/** 
	 * 职业
	 */
	@ApiField("profession")
	private String profession;

	/** 
	 * 省份名称
	 */
	@ApiField("province")
	private String province;

	/** 
	 * 淘宝id
	 */
	@ApiField("taobao_id")
	private String taobaoId;

	/** 
	 * 支付宝用户的userId
	 */
	@ApiField("user_id")
	private String userId;

	/** 
	 * 若用户是个人用户，则是用户的真实姓名；若是企业用户，则是企业名称。
	 */
	@ApiField("user_name")
	private String userName;

	/** 
	 * 用户状态（Q/T/B/W）。
Q代表快速注册用户
T代表已认证用户
B代表被冻结账户
W代表已注册，未激活的账户
	 */
	@ApiField("user_status")
	private String userStatus;

	/** 
	 * 用户类型（1/2）
1代表公司账户2代表个人账户
	 */
	@ApiField("user_type")
	private String userType;

	/** 
	 * 邮政编码。
	 */
	@ApiField("zip")
	private String zip;

	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress( ) {
		return this.address;
	}

	public void setArea(String area) {
		this.area = area;
	}
	public String getArea( ) {
		return this.area;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getAvatar( ) {
		return this.avatar;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getBusinessScope( ) {
		return this.businessScope;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCertNo( ) {
		return this.certNo;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertType( ) {
		return this.certType;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getCity( ) {
		return this.city;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryCode( ) {
		return this.countryCode;
	}

	public void setDeliverAddresses(List<AlipayUserDeliverAddress> deliverAddresses) {
		this.deliverAddresses = deliverAddresses;
	}
	public List<AlipayUserDeliverAddress> getDeliverAddresses( ) {
		return this.deliverAddresses;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail( ) {
		return this.email;
	}

	public void setFirmAgentPersonCertExpiryDate(String firmAgentPersonCertExpiryDate) {
		this.firmAgentPersonCertExpiryDate = firmAgentPersonCertExpiryDate;
	}
	public String getFirmAgentPersonCertExpiryDate( ) {
		return this.firmAgentPersonCertExpiryDate;
	}

	public void setFirmAgentPersonCertNo(String firmAgentPersonCertNo) {
		this.firmAgentPersonCertNo = firmAgentPersonCertNo;
	}
	public String getFirmAgentPersonCertNo( ) {
		return this.firmAgentPersonCertNo;
	}

	public void setFirmAgentPersonCertType(String firmAgentPersonCertType) {
		this.firmAgentPersonCertType = firmAgentPersonCertType;
	}
	public String getFirmAgentPersonCertType( ) {
		return this.firmAgentPersonCertType;
	}

	public void setFirmAgentPersonName(String firmAgentPersonName) {
		this.firmAgentPersonName = firmAgentPersonName;
	}
	public String getFirmAgentPersonName( ) {
		return this.firmAgentPersonName;
	}

	public void setFirmLegalPersonCertExpiryDate(String firmLegalPersonCertExpiryDate) {
		this.firmLegalPersonCertExpiryDate = firmLegalPersonCertExpiryDate;
	}
	public String getFirmLegalPersonCertExpiryDate( ) {
		return this.firmLegalPersonCertExpiryDate;
	}

	public void setFirmLegalPersonCertNo(String firmLegalPersonCertNo) {
		this.firmLegalPersonCertNo = firmLegalPersonCertNo;
	}
	public String getFirmLegalPersonCertNo( ) {
		return this.firmLegalPersonCertNo;
	}

	public void setFirmLegalPersonCertType(String firmLegalPersonCertType) {
		this.firmLegalPersonCertType = firmLegalPersonCertType;
	}
	public String getFirmLegalPersonCertType( ) {
		return this.firmLegalPersonCertType;
	}

	public void setFirmLegalPersonName(String firmLegalPersonName) {
		this.firmLegalPersonName = firmLegalPersonName;
	}
	public String getFirmLegalPersonName( ) {
		return this.firmLegalPersonName;
	}

	public void setFirmLegalPersonPictures(List<AlipayUserPicture> firmLegalPersonPictures) {
		this.firmLegalPersonPictures = firmLegalPersonPictures;
	}
	public List<AlipayUserPicture> getFirmLegalPersonPictures( ) {
		return this.firmLegalPersonPictures;
	}

	public void setFirmPictures(List<AlipayUserPicture> firmPictures) {
		this.firmPictures = firmPictures;
	}
	public List<AlipayUserPicture> getFirmPictures( ) {
		return this.firmPictures;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}
	public String getFirmType( ) {
		return this.firmType;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGender( ) {
		return this.gender;
	}

	public void setIsBalanceFrozen(String isBalanceFrozen) {
		this.isBalanceFrozen = isBalanceFrozen;
	}
	public String getIsBalanceFrozen( ) {
		return this.isBalanceFrozen;
	}

	public void setIsCertified(String isCertified) {
		this.isCertified = isCertified;
	}
	public String getIsCertified( ) {
		return this.isCertified;
	}

	public void setIsStudentCertified(String isStudentCertified) {
		this.isStudentCertified = isStudentCertified;
	}
	public String getIsStudentCertified( ) {
		return this.isStudentCertified;
	}

	public void setLicenseExpiryDate(String licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}
	public String getLicenseExpiryDate( ) {
		return this.licenseExpiryDate;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getLicenseNo( ) {
		return this.licenseNo;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
	public String getMemberGrade( ) {
		return this.memberGrade;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile( ) {
		return this.mobile;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getNickName( ) {
		return this.nickName;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationCode( ) {
		return this.organizationCode;
	}

	public void setPersonBirthday(String personBirthday) {
		this.personBirthday = personBirthday;
	}
	public String getPersonBirthday( ) {
		return this.personBirthday;
	}

	public void setPersonCertExpiryDate(String personCertExpiryDate) {
		this.personCertExpiryDate = personCertExpiryDate;
	}
	public String getPersonCertExpiryDate( ) {
		return this.personCertExpiryDate;
	}

	public void setPersonPictures(List<AlipayUserPicture> personPictures) {
		this.personPictures = personPictures;
	}
	public List<AlipayUserPicture> getPersonPictures( ) {
		return this.personPictures;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone( ) {
		return this.phone;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getProfession( ) {
		return this.profession;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvince( ) {
		return this.province;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}
	public String getTaobaoId( ) {
		return this.taobaoId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId( ) {
		return this.userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName( ) {
		return this.userName;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserStatus( ) {
		return this.userStatus;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserType( ) {
		return this.userType;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip( ) {
		return this.zip;
	}

}
