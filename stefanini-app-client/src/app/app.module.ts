import { NgModule, LOCALE_ID  } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { registerLocaleData } from '@angular/common';
import ptBr from '@angular/common/locales/pt';

import { AppComponent } from './app.component';
import { JwtInterceptor } from './security/interceptors/jwt.interceptor';
import { ErrorInterceptor } from './security/interceptors/error.interceptor';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { HomeComponent } from './home/home.component';
import { FuncionariosComponent } from './funcionarios/funcionarios.component';
import { FuncionarioDetalhesComponent } from './funcionarios/funcionario-detalhes/funcionario-detalhes.component';
import { AvaliacoesComponent } from './avaliacoes/avaliacoes.component';
import { AvaliacaoFormComponent } from './avaliacoes/avaliacao-form/avaliacao-form.component';
import { AvaliacaoAprovarComponent } from './avaliacoes/avaliacao-aprovar/avaliacao-aprovar.component';

registerLocaleData(ptBr)

@NgModule({
	declarations: [
		AppComponent,
		LoginComponent,
		RegisterComponent,
		UserDetailsComponent,
		HomeComponent,
		FuncionariosComponent,
		FuncionarioDetalhesComponent,
		AvaliacoesComponent,
		AvaliacaoFormComponent,
		AvaliacaoAprovarComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule,
		AppRoutingModule
	],
	providers: [
		{ provide: LOCALE_ID, useValue: 'pt-BR' },
		{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
