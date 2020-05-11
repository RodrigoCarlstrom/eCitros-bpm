<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="UTF-8">
		<title>${subject}</title>
	</head>
	<body
		style="padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;">
		<div style="padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;">
			<div class="align-items-center"	style="margin-top: 15px; margin-bottom: 15px;">
				<div style="-ms-flex: 0 0 100%; flex: 0 0 100%; max-width: 100%; text-align: center !important;">
					<div width="200px">
						<#include "/logo.ftl"/>
					</div>
				</div>
			</div>
			<div style="-ms-flex: 0 0 100%; flex: 0 0 100%; max-width: 100%;">
				<div style="padding: 2rem 1rem; margin-bottom: 2rem; background-color: #e9ecef; border-radius: 0.3rem;">
					<h1>${title}</h1>
					<p style="font-size: 1.25rem; font-weight: 300;">${message}</p>
					<hr style="margin-top: 1.5rem !important;">
					<#if instruction??>
						<p>${instruction}</p>
						<#list actions as action>
						  <button href="${action.action}" role="button" style="display: inline-block; font-weight: 400; color: #fff; background-color: #007bff; text-align: center; vertical-align: middle; cursor: pointer; -webkit-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none; border: 1px solid #007bff; padding: 0.375rem 0.75rem; font-size: 1rem; line-height: 1.5; border-radius: 0.25rem; ">${action.label}</button>
						</#list>
					</#if>
				</div>
			</div>
			<table style="border: 0px; width: 100%;">
				<tbody style="width: 100%;">
					<tr>
						<td style="width: 50%;">
							<address>
								<div width="100px">
									<#include "/logo.ftl"/>
								</div>
								<br/> 
								Av. Dr. Luiz Migliano, 190
								<br/>
								Morumbi - São Paulo - SP
								<br/> 
								CEP: 05711-000
								<br/> 
								<abbr title="Telefone">Fone: (11) 94114-9380</abbr><br/>
								<abbr title="WhatsApp">WhatsApp: (19) 98116-8066</abbr><br/> 
								<a href="mailto:contato@evoluosoftware.com.br">contato@evoluosoftware.com.br</a>
							</address>
						</td>
						<td style="width: 50%; text-align: right !important;">
							<p>Potencializado por</p>
							<p>
								<div width="100px">
									<#include "/evoluo.ftl"/>
								</div>
							</p>
							<p>
								<small>Todos direitos reservados</small>
							</p>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>