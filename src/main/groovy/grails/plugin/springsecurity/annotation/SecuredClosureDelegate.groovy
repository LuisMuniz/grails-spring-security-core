/* Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.springsecurity.annotation

import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.context.ApplicationContext
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.authentication.AuthenticationTrustResolver
import org.springframework.security.core.Authentication
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot

import grails.web.servlet.mvc.GrailsParameterMap
import groovy.transform.CompileStatic

/**
 * Set as the delegate of a closure in @Secured annotations; provides access to the request and application context,
 * as well as all of the methods and properties available when using SpEL.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
@CompileStatic
class SecuredClosureDelegate extends WebSecurityExpressionRoot {

	ApplicationContext ctx

	SecuredClosureDelegate(Authentication a, FilterInvocation fi, ApplicationContext ctx) {
		super(a, fi)
		this.ctx = ctx
		setTrustResolver ctx.getBean('authenticationTrustResolver', AuthenticationTrustResolver)
		setRoleHierarchy ctx.getBean('roleHierarchy', RoleHierarchy)
		setPermissionEvaluator ctx.getBean('permissionEvaluator', PermissionEvaluator)
	}

	GrailsParameterMap getParams() {
		GrailsWebRequest gwr = (GrailsWebRequest)request.getAttribute(GrailsApplicationAttributes.WEB_REQUEST)
		gwr?.params
	}
}
